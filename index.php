<?php
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;
require '../myslim/vendor/autoload.php';
$app = new \Slim\App;

class DbConnect {
	function getDB () {
		$dbhost = "finalproject.com";
		$dbuser = "root";
		$dbpass = "";
		$dbname = "final_project";
		$conn = new mysqli($dbhost,$dbuser,$dbpass,$dbname);
		if ($conn -> connect_error )		{
			die (" Connection failed :") ;
		}
		else
		{
			//echo "connection succesfuul";
		}
		return $conn ;
	}
}

class DbHandler {
private $conn ;
function __construct () {
//require_once dirname ( __FILE__ ) . '/ db_connect . php ';
// opening db connection
$db = new DbConnect () ;
$this -> conn = $db->getDB () ;
}
public function Close () {
$this -> conn -> close () ;
}
public function getData($uid)
{
	//$stmt = $this->conn->prepare("select uid,company,sum(stocks_count) as count,avg(price) as price from transaction where uid = ? GROUP BY uid,company");
$stmt = $this->conn->prepare("select uid,company,stocks_count as count,price as price from transaction where uid = ? ");	
$stmt -> bind_param ("s" , $uid );
if($stmt -> execute () )
	{
		$trans = $stmt->get_result () ;
		$response = array () ;
		if($trans != NULL)
		{
			
			$return_arr = array();
			
            while($mov = $trans->fetch_assoc())
			{
				
				$tmp['uid']=$mov['uid'];
				$tmp['company']=$mov['company'];
				$tmp['count']=$mov['count'];
				$tmp['price']=$mov['price'];
				array_push($return_arr, $tmp);
			}
            $js= json_encode($return_arr);
			echo $js;			
		}
		$stmt -> close () ;
	}
	else{
		echo "No results";
	}
}
public function getDetails($uid)
{
	$stmt = $this->conn->prepare("SELECT Avail_amount,Spent_amount FROM `amount` WHERE uid=? ");
	$stmt -> bind_param ("s" , $uid );
	if($stmt -> execute () )
	{
		$trans = $stmt->get_result () ;
		$tmp=array();
		if($mov= $trans->fetch_assoc())
		{
			/*$tmp['row_id']=$mov['row_id'];
			$tmp['UID']=$mov['UID'];*/
			$tmp['Avail_amount']=$mov['Avail_amount'];
			$tmp['Spent_amount']=$mov['Spent_amount'];
		}
		$js=json_encode($tmp);
		echo $js;
		$stmt -> close () ;
	}
	else{
		echo "No results";
	}
	
}
public function deleteTrans($mov)
{
	//DELETE FROM `transaction2` WHERE UID=? AND Company=? AND Stocks_Count=? AND Price =?
	echo "inside DeleteTrans";
	$stmt2 = $this->conn->prepare("DELETE FROM `transaction` WHERE UID=? AND Company=? AND Stocks_Count=? AND Price =?");
	$stmt2->bind_param("ssid",$mov['uid'],$mov['company'],$mov['count'],$mov['price']);
	echo "before executing";
	$x=$mov['count']*$mov['price'];
	//UPDATE `amount2` SET Avail_amount=Avail_amount+20,Spent_amount=Spent_amount-20 WHERE UID = "jkcFD1klHzYaWtRHMEeh7zT9PS63"
	//UPDATE `amount2` SET Avail_amount=Avail_amount+20,Spent_amount=Spent_amount-20 WHERE UID = "jkcFD1klHzYaWtRHMEeh7zT9PS63"
	$stmt1 = $this->conn->prepare("UPDATE amount SET Avail_amount=Avail_amount+?,Spent_amount=Spent_amount-? WHERE UID = ?");
	$stmt1->bind_param("dds",$x,$x,$mov['uid']);
	echo "ater executng";
	if($stmt2->execute() && $stmt1->execute() )
	{
		echo "delete in Transaction";
		
		
	}
	else
	{
		echo "false";
	}
	
}
public function AddUser($mov)
{
	$stmt2 = $this->conn->prepare("INSERT INTO `amount`(`UID`, `Avail_amount`, `Spent_amount`) VALUES (?,?,?)");
	$stmt2->bind_param("sdd",$mov['uid'],$mov['Avail_amount'],$mov['Spent_amount']);
	if($stmt2->execute())
	{
		echo "User added";
	}
	else{
		echo "Not psosisble";
	}
}
public function AddMoney($mov)
{
	$stmt1 = $this->conn->prepare("UPDATE amount SET Avail_amount=Avail_amount+? WHERE UID = ?");
	$stmt1->bind_param("ds",$mov['amount'],$mov['uid']);
	if($stmt1->execute())
	{
		echo "Added money";
	}
	else{
		echo "not possible";
	}
	
}
public function DelMoney($mov)
{
	$stmt1 = $this->conn->prepare("UPDATE amount SET Avail_amount=Avail_amount-? WHERE UID = ?");
	$stmt1->bind_param("ds",$mov['amount'],$mov['uid']);
	if($stmt1->execute())
	{
		echo "Withdrawed money";
	}
	else{
		echo "not possible";
	}

}

public function AddTrans($mov)
{
	$stmt2 = $this->conn->prepare("INSERT INTO `transaction`(`Company`,`UID`, `Stocks_Count`, `Price`) VALUES (?,?,?,?)");
	$stmt2->bind_param("ssdd",$mov['Company'],$mov['uid'],$mov['Stocks_Count'],$mov['Price']);
	$x=$mov['Stocks_Count']*$mov['Price'];
	$stmt1 = $this->conn->prepare("UPDATE amount SET Avail_amount=Avail_amount-?,Spent_amount=Spent_amount+? WHERE UID = ?");
	$stmt1->bind_param("dds",$x,$x,$mov['uid']);
	
	
	if($stmt2->execute() && $stmt1->execute())
	{
		echo "User Transaction";
	}
	else{
		echo "Not psosisble";
	}
	
	
}


}

$app->get('/uid/{uid}', function (Request $request, Response $response, array $args)
{
	//echo "hello";
    $response = array () ;
	$uid = $args['uid'];
	$db = new DbHandler() ;
	$db->getData($uid) ;
	$db -> Close () ;
});
$app->get('/details/{uid}', function (Request $request, Response $response, array $args)
 {
    $response = array () ;
	$uid = $args['uid'];
	$db = new DbHandler() ;
	$db->getDetails($uid) ;
	$db -> Close () ;
});

$app -> post('/delete',function (Request $request, Response $response) {
	echo "dub";
	$db = new DbHandler();
    //$dat = $request->getParsedBody();
	//$data = json_decode($request->getBody(),true
	//$va=json_encode($request->getBody());
	echo "before";
	echo $request->getBody();
	echo "after";
	$data=json_decode($request->getBody(),true);
	//$data=$request->getParsedBody();
	$mov =array();
	$mov['uid']=$data['uid'];
	$mov['company']=$data['company'];
	$mov['count']=$data['count'];
	$mov['price']=$data['price'];
	
	$db->deleteTrans($mov);
	$db -> Close () ;
}
);

$app -> post('/add_user',function (Request $request, Response $response) {
	echo "dub";
	$db = new DbHandler();
    //$dat = $request->getParsedBody();
	//$data = json_decode($request->getBody(),true
	//$va=json_encode($request->getBody());
	echo "before";
	echo $request->getBody();
	echo "after";
	$data=json_decode($request->getBody(),true);
	//$data=$request->getParsedBody();
	$mov =array();
	$mov['uid']=$data['uid'];
	$mov['Avail_amount']=$data['Avail_amount'];
	$mov['Spent_amount']=$data['Spent_amount'];
	//$mov['price']=$data['price'];
	
	$db->AddUser($mov);
	$db -> Close () ;
}
);
$app -> post('/add_Trans',function (Request $request, Response $response) {
	echo "dub";
	$db = new DbHandler();
    //$dat = $request->getParsedBody();
	//$data = json_decode($request->getBody(),true
	//$va=json_encode($request->getBody());
	echo "before";
	echo $request->getBody();
	echo "after";
	$data=json_decode($request->getBody(),true);
	//$data=$request->getParsedBody();
	$mov =array();
	
	
	$mov['Company']=$data['Company'];
	$mov['uid']=$data['uid'];
	$mov['Stocks_Count']=$data['Stocks_Count'];
	$mov['Price']=$data['Price'];
	
	$db->AddTrans($mov);
	$db -> Close () ;
}
);

$app -> post('/add_money',function (Request $request, Response $response) {
	echo "dub";
	$db = new DbHandler();
    //$dat = $request->getParsedBody();
	//$data = json_decode($request->getBody(),true
	//$va=json_encode($request->getBody());
	echo "before";
	echo $request->getBody();
	echo "after";
	$data=json_decode($request->getBody(),true);
	
	$mov =array();
	
	
	$mov['uid']=$data['uid'];
	$mov['amount']=$data['amount'];
	
	
	$db->AddMoney($mov);
	$db -> Close () ;
}
);
$app -> post('/del_money',function (Request $request, Response $response) {
	echo "dub";
	$db = new DbHandler();
    //$dat = $request->getParsedBody();
	//$data = json_decode($request->getBody(),true
	//$va=json_encode($request->getBody());
	echo "before";
	echo $request->getBody();
	echo "after";
	$data=json_decode($request->getBody(),true);
	
	$mov =array();
	
	
	$mov['uid']=$data['uid'];
	$mov['amount']=$data['amount'];
	
	
	$db->DelMoney($mov);
	$db -> Close () ;
}
);



$app -> run () ;
?>