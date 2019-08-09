package com.example.saisekhar.finalproject1


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_purchase.*
import kotlinx.android.synthetic.main.fragment_addmoney.*
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class addmoney : Fragment() {
    companion object {
        fun newInstance():addmoney
        {
            addmoney().apply {
            }
            return addmoney()
        }
    }
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }
    var uid = FirebaseAuth . getInstance () . uid ?: ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addmoney, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("inside add", "add money fragemnt")
        add_btn.setOnClickListener{
            var p=add_qty.text.toString().toDouble()
            var json: JSONObject = JSONObject()
            var cont=Myapp.getContext()
            var base=cont.getString(R.string.url_base)
            var url1=base+"add_money"
            json.put("uid",uid)
            json.put("amount",p)
            val runnable = Runnable {
                var cont=Myapp.getContext()
                var base=cont.getString(R.string.url_base)

                var url=base+"add_money"
                Log.i("inside runnable","value runnable")
                val r=MyUtility.sendHttPostRequest(url, json.toString())

            }
            Thread(runnable).start()
            add_text.text="successfully done"
            add_qty.text.clear()

        }
        change.setOnClickListener {
            mlist!!.onchange()
        }
    }
    interface addmoneyinterface{
        fun onchange()
    }
    var mlist:addmoneyinterface?=null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is addmoneyinterface)
        {
            mlist=context
        }
    }
    override fun onDetach () {
        super . onDetach ()
        mlist = null
    }


}
