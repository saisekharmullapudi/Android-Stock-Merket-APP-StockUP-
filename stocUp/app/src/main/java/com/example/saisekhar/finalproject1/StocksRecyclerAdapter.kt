package com.example.saisekhar.finalproject1

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.cardview.view.*

public class StocksRecyclerAdapter(val context: Stocks) : RecyclerView.Adapter<StocksRecyclerAdapter.MyViewHolder>()
{
    var data= ArrayList<StocksJSON>()
    lateinit var mlistner:CustomOnClickStocks
    interface CustomOnClickStocks  {
        fun CustomOnClick(stc:StocksJSON);
    }
    fun setliternerInterface(listn :CustomOnClickStocks)
    {
        mlistner=listn

    }

    val TAG = "FB Adapter "
    private val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val mRef = mDatabase.child("stocks")
    var childEvent = object : ChildEventListener {
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            Log .d(TAG , " child event listener - onChildMoved " +p0. toString () )

        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            Log .d(TAG , " child event listener - onChildChanged " +p0. toString () )

        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            Log .d(TAG , " child event listener - onChildChanged " +p0. toString () )
            var item=p0.getValue<StocksJSON>(StocksJSON::class.java)
            if (item != null) {
                data.add(item)
            }
            Log.i("items size",data.size.toString())
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
        val addChildEventListener = mRef.addChildEventListener(childEvent)

    }
    public fun ChangeData(value:String)
    {

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): StocksRecyclerAdapter.MyViewHolder {
        val v: View

        return MyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.cardview, p0, false))
    }

    override fun getItemCount(): Int {
        return data.size;
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(p0: StocksRecyclerAdapter.MyViewHolder, p1: Int) {
        p0.company_name.text=data[p1].comapny
        p0.company_value.setTextColor(Color.parseColor("#f71b1b"))
//        p0.company_value.text=data[p1].open.toString()
        if(data[p1].open==data[p1].close)
        {

            p0.company_value.text=data[p1].open.toString()
            p0.company_value.setTextColor(Color.GRAY)
        }else if(data[p1].open!!> data[p1].close!!)
        {

            p0.company_value.text=data[p1].open.toString()
            p0.company_value.setTextColor(Color.GREEN)
        }else if(data[p1].open!!< data[p1].close!!)
        {

            p0.company_value.text=data[p1].open.toString()
            p0.company_value.setTextColor(Color.RED)
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
            anim.duration=3500
            itemView.startAnimation(anim)
            lp=p1
        }
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var company_name=itemView.company_name
        var company_value=itemView.company_value
        init{
            itemView.setOnClickListener{
                if(mlistner!=null)
                {
                    if(adapterPosition!=RecyclerView.NO_POSITION)
                    {
                        Log.i("inside Stocks Adapter","working")
                        mlistner.CustomOnClick(data[adapterPosition])
                    }
                }

            }

        }

    }

}