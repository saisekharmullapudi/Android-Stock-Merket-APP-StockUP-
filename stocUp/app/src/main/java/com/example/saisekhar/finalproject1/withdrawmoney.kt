package com.example.saisekhar.finalproject1


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_purchase_crypto.*
import kotlinx.android.synthetic.main.activity_sell.*
import kotlinx.android.synthetic.main.fragment_withdrawmoney.*
import org.json.JSONObject
import java.lang.ref.WeakReference


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class withdrawmoney : Fragment() {
    var money :value= value()
    companion object {
        fun newInstance():withdrawmoney
        {
            addmoney().apply {
            }
            return withdrawmoney()
        }
    }

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
        Log.i("inside withdraw money","withdraw fragment")
        var task=getBuyPower(money)
        var cont=Myapp.getContext()
        var base=cont.getString(R.string.url_base)
        var url1=base+"details/"+uid
        task.execute(url1)
    }

    var uid = FirebaseAuth . getInstance () . uid ?: ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdrawmoney, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with_btn.setOnClickListener {
            var json:JSONObject= JSONObject()
            json.put("uid",uid.toString())
            json.put("amount",with_qty.text.toString().toDouble())
            if(money.buy_power!!>=with_qty.text.toString().toDouble())
            {
                val runnable = Runnable {
                    var cont=Myapp.getContext()
                    var base=cont.getString(R.string.url_base)
                    val url = base+"del_money"

                    val r=MyUtility.sendHttPostRequest(url, json.toString())
                    Log.i("Successfuly done", "Withdrawing")
                }
                Thread(runnable).start()

            }
            else
            {
                var str:String=""


            }
//            Crypto_text.text="Successfully done"
//            Crypto_qty.text.clear()
        }
        change1.setOnClickListener {
            mlist!!.onadd()

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
    interface withdrawinterface
    {
        fun onadd()
    }
    var mlist:withdrawinterface?=null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is withdrawinterface)
        {
            mlist=context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mlist=null
    }
}
