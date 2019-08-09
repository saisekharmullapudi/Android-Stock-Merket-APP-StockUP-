package com.example.saisekhar.finalproject1

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.cardview.view.*
import kotlinx.android.synthetic.main.cardview_crypto.view.*

public class CryptoRecyclerAdapter(val context: Cryptocurrency) : RecyclerView.Adapter<CryptoRecyclerAdapter.MyViewHolder>()
{
    var data1= ArrayList<CryptoJSON>()
    lateinit var mlistner1:CustomOnClickCrypto
    interface CustomOnClickCrypto  {
        fun CustomOnCrypto(stc:CryptoJSON);
    }
    fun setListner(listner: CustomOnClickCrypto)
    {
        mlistner1=listner
    }
    val TAG = "FB Adapter "
    private val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val mRef = mDatabase.child("cryptocurrency")

    var childEvent1 = object : ChildEventListener {
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            Log .d(TAG , " child event listener - onChildMoved " +p0. toString () )

        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            Log .d(TAG , " child event listener - onChildChanged " +p0. toString () )

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            Log .d(TAG , " child event listener - onChildChanged " +p0. toString () )
            var item=p0.getValue<CryptoJSON>(CryptoJSON::class.java)
            if (item != null) {
                data1.add(item)
            }
            Log.i("items size",data1.size.toString())
            notifyDataSetChanged()
        }

        override fun onChildRemoved(p0: DataSnapshot) {
            Log .d(TAG , " child event listener - onChildChanged " +p0. toString () )

        }

        override fun onCancelled(p0: DatabaseError) {
            Log.d(TAG, " child event listener - onCancelled " + p0.toException())
        }

    }
    init {
        val addChildEventListener = mRef.addChildEventListener(childEvent1)

    }



    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.crypto_name.text=data1[p1].From_Currency
        p0.crypto_value.text=data1[p1].Exchange_Rate.toString()
        if(data1[p1].Exchange_Rate==20.00)
        {

            p0.crypto_value.text=data1[p1].Exchange_Rate.toString()
            p0.crypto_value.setTextColor(Color.GRAY)
        }else if(data1[p1].Exchange_Rate!!>20.00!!)
        {

            p0.crypto_value.text=data1[p1].Exchange_Rate.toString()
            p0.crypto_value.setTextColor(Color.GREEN)
        }else if(data1[p1].Exchange_Rate!!<20.00!!)
        {

            p0.crypto_value.text=data1[p1].Exchange_Rate.toString()
            p0.crypto_value.setTextColor(Color.RED)
        }
        setAnim(p0.itemView,p1)

    }
    var lp=-1

    @SuppressLint("ResourceType")
    fun setAnim(itemView: View, p1: Int)
    {
        if(p1>lp)
        {
//            var ani:Animation=AnimationUtils.loadAnimation(cont,R.transition.slide_in_left)
            var anim: AlphaAnimation =  AlphaAnimation(0.0f, 1.0f)
            anim.duration=1500
            itemView.startAnimation(anim)
            lp=p1
        }
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val v: View

        return MyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.cardview_crypto, p0, false))
    }

    override fun getItemCount(): Int {
     return data1.size;
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var crypto_name=itemView.crypto_name
        var crypto_value=itemView.crypto_value
        init{
            itemView.setOnClickListener {
                if (mlistner1 != null) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        Log.i("inside Stocks Adapter", "working")
                        mlistner1.CustomOnCrypto(data1[adapterPosition])
                    }
                }
            }




        }

    }

}