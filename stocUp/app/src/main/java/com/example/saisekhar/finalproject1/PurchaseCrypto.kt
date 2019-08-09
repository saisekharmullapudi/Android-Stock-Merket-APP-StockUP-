package com.example.saisekhar.finalproject1

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_purchase_crypto.*
import kotlinx.android.synthetic.main.activity_sell.*
import org.json.JSONObject
import java.lang.ref.WeakReference

class PurchaseCrypto : AppCompatActivity() {

    var money :value= value()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_crypto)
        var item_value:CryptoJSON= intent.getSerializableExtra("crypto_data") as CryptoJSON
        Log.i("value",item_value.toString())
        var uid = FirebaseAuth . getInstance () . uid ?: ""
//            var uid = FirebaseAuth . getInstance () . uid ?: ""
        var task=getBuyPower(money)
        var cont=Myapp.getContext()
        var base=cont.getString(R.string.url_base)
        var url1=base+"details/"+uid
        task.execute(url1)


        CryptoCompany.text=item_value.From_Currency.toString()
        crypto_price.text=item_value.Exchange_Rate.toString()
//        var x:Double=sell_qty.text.toString().toDouble()
        Crypto_btn.setOnClickListener {




            var json: JSONObject = JSONObject()
            json.put("Company",item_value.From_Currency.toString())
            json.put("uid",uid)
            json.put("Stocks_Count",Crypto_qty.text.toString().toDouble())
            json.put("Price",item_value.Exchange_Rate.toString().toDouble())
            if(money.buy_power!!>=(Crypto_qty.text.toString().toDouble()*item_value.Exchange_Rate.toString().toDouble()))
            {
                val runnable = Runnable {
                    var cont=Myapp.getContext()
                    var base=cont.getString(R.string.url_base)
                    val url = base+"add_Trans"

                    val r=MyUtility.sendHttPostRequest(url, json.toString())
                    if(r==true.toString())
                    {
                        sell_text.text="Succesfull"
                    }

                }
                Thread(runnable).start()
                Crypto_text.text="Successfully done"
                Crypto_qty.hint="enter new quantity"
            }
            else
            {
                Crypto_text.text="Not enough funds"
            }





        }
    }


    inner class getBuyPower(mon:value): AsyncTask<String, Void, String>()
    {
        val weakData1 = WeakReference<value>(mon)
//        val weakData2 = WeakReference<TextView>(mon1)

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
//                var l2=weakData2.get()
                var job: JSONObject = JSONObject(result)
                Log.i("value in string",job.toString())
                Log.i("value Avail amount",job.getDouble("Avail_amount").toString())
                Log.i("value Spent_amount",job.getDouble("Spent_amount").toString())
//                var pq=job.getDouble("Avail_amount")
//                val pr=job.getBoolean("Spent_amount")
//                var mov = value(job.getDouble("portfo"), job.getDouble("buy_power"))
//                l1!!.text=job.getDouble("Avail_amount").toString()
//                l2!!.text=job.getDouble("Spent_amount").toString()
                l1!!.portfo=job.getDouble("Spent_amount").toString().toDouble()
                l1!!.buy_power=job.getDouble("Avail_amount").toString().toDouble()
                change(l1)


            }
        }

    }
    fun change(mon1:value)
    {
        money=mon1
        Log.i("money value", money.buy_power.toString())
    }

}
