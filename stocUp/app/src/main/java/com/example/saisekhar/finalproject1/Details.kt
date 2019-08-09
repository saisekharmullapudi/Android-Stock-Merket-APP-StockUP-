package com.example.saisekhar.finalproject1


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.example.saisekhar.finalproject1.R.id.portfolio_value
import com.google.firebase.auth.FirebaseAuth
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import org.json.JSONArray
import org.json.JSONObject
import java.lang.ref.WeakReference
import lecho.lib.hellocharts.view.PieChartView
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.model.PieChartData
import java.math.RoundingMode
import java.text.DecimalFormat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Details : Fragment(), DetailRecyclerAdapter.detailsInterface {
    override fun setAction(view: View,trns: Transaction) {
        /*recyclerAdapter.changeTrans(trns)
        var uid = FirebaseAuth . getInstance () . uid ?: ""
        var c1=par1.text.toString().toDouble()
        var c2=par2.text.toString().toDouble()

        c1=c1+(trns.count!!* trns.price!!)
        c2=c2-(trns.count!!*trns.price!!)
        par1.text=c1.toString()
        par1.text=c2.toString()

        val json = JSONObject()
        json.put("UID",uid)
        json.put("Company",trns.company)*/

        listner.function(view,trns)

    }

    companion object {
        fun newInstance():Details
        {
            Details().apply {

            }
            return Details()
        }
    }
    lateinit var listner:DetailTrs
    lateinit var recyclerView : RecyclerView
    lateinit var recyclerAdapter: DetailRecyclerAdapter
    interface DetailTrs
    {
        fun function(view: View,tp:Transaction)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Log.i("Inside Detail","Fragment")

        val rootView= inflater.inflate(R.layout.fragment_details, container, false)
        recyclerView= rootView.findViewById(R.id.recViewDetails)
        par1=rootView.findViewById(R.id.portfolio_value)
        par2=rootView.findViewById(R.id.buying_power)

        var uid = FirebaseAuth . getInstance () . uid ?: ""
        Log.i("uid in Detail Fragment",uid.toString())
        var cont=Myapp.getContext()
        var base=cont.getString(R.string.url_base)
        var url=base+"details/"+uid
        val pieChartView:PieChartView = rootView.findViewById(R.id.chart)
        var task=getBuyPower(par1,par2,pieChartView)
        task.execute(url)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView.setLayoutManager(mLayoutManager)

        recyclerAdapter= DetailRecyclerAdapter(Details())
        recyclerView.adapter=recyclerAdapter;
        recyclerAdapter.setListner(this)

//        recyclerView.itemAnimator=SlideInLeftAnimator()
//        recyclerView.getItemAnimator()!!.setAddDuration(3000);


//        val pieChartView:PieChartView = rootView.findViewById(R.id.chart)
//        val pieData = ArrayList<SliceValue>()
//
////        val c:Double= 10.00
////        val d:Double=60.00
//        pieData.add(SliceValue(a.toFloat(), Color.BLUE))
//        pieData.add(SliceValue(b.toFloat(), Color.GRAY));
////        pieData.add(SliceValue(c.toFloat(), Color.RED));
////        pieData.add(SliceValue(d.toFloat(), Color.MAGENTA))
//
//        val pieChartData = PieChartData(pieData)
//        pieChartView.setPieChartData(pieChartData);




        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is DetailTrs)
        {
            listner=context
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    init {
        /*var uid = FirebaseAuth . getInstance () . uid ?: ""
        Log.i("uid in Detail Fragment",uid.toString())
        var url="http://finalproject.com/details/"+uid
        val money:value
        money= value(0.0,0.0)
        var task=getBuyPower(money)
        task.execute(url)*/

    }
//    var strp=context!!.getString(R.string.url_base)
    lateinit var par1: TextView
    lateinit var par2: TextView
    val money:value= value()
//    var a:Double=0.0
//    var b:Double=0.0
    inner class getBuyPower(mon:TextView,mon1:TextView,mone:PieChartView):AsyncTask<String,Void,String>()
    {
        val weakData1 = WeakReference<TextView>(mon)
        val weakData2 = WeakReference<TextView>(mon1)
        val weakData3 = WeakReference<PieChartView>(mone)

        override fun doInBackground(vararg params: String?): String? {
            var res = MyUtility.downloadJSONusingHTTPGetRequest(params[0]!!)
            Log.i("download result ", res.toString())
            if(res!=null)
            {
                return res!!
            }
            else
            {
                return null
            }

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                result.trimIndent()
                var l1 = weakData1.get()
                var l2=weakData2.get()
                val l3 :PieChartView= weakData3.get()!!
                var job: JSONObject = JSONObject(result)
                Log.i("value in string",job.toString())
                Log.i("value Avail amount",job.getDouble("Avail_amount").toString())
                Log.i("value Spent_amount",job.getDouble("Spent_amount").toString())
//                var pq=job.getDouble("Avail_amount")
//                val pr=job.getBoolean("Spent_amount")
//                var mov = value(job.getDouble("portfo"), job.getDouble("buy_power"))
                val df = DecimalFormat("#.###")
                df.roundingMode = RoundingMode.CEILING

                val df1 = DecimalFormat("#.###")
                df1.roundingMode = RoundingMode.CEILING

                val p=df.format(job.getDouble("Spent_amount").toString().toDouble())
                Log.i("p value", p.toString())

                val q= df1.format(job.getDouble("Avail_amount").toString().toDouble())
                l1!!.text=q.toString()
                l2!!.text=p.toString()
                var a:Double=job.getDouble("Avail_amount").toString().toDouble()
                var b:Double=job.getDouble("Spent_amount").toString().toDouble()
                val pieData = ArrayList<SliceValue>()
                pieData.add(SliceValue(b.toFloat(), Color.argb(255,17,26,35)))
                pieData.add(SliceValue(a.toFloat(), Color.WHITE))
                val pieChartData = PieChartData(pieData)
                l3.setPieChartData(pieChartData);


            }
        }

    }


}
