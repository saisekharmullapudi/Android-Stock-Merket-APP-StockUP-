package com.example.saisekhar.finalproject1


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import com.example.saisekhar.finalproject1.Stocks.SetONCLick
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Stocks : Fragment(), StocksRecyclerAdapter.CustomOnClickStocks {

    var num:Int=0
    lateinit var listner:SetONCLick
    lateinit var recyclerView : RecyclerView
    lateinit var recyclerAdapter: StocksRecyclerAdapter
    interface SetONCLick
    {
        fun Eventclick(data: StocksJSON)
    }
    companion object{
        @JvmStatic
        fun newInstance():Stocks
        {
            Stocks().apply {

            }
            return Stocks()
        }
    }




    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_stocks, container, false)
//        listner = rootView.context as SetONCLick
        recyclerView= rootView.findViewById(R.id.recViewMaster)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView.setLayoutManager(mLayoutManager)
//        cont=container!!.context
        recyclerAdapter= StocksRecyclerAdapter(Stocks())
        recyclerView.adapter=recyclerAdapter;
        recyclerAdapter.setliternerInterface(this)

        recyclerView.itemAnimator = SlideInLeftAnimator()
        val animator = SlideInUpAnimator(OvershootInterpolator(1f))
        recyclerView.itemAnimator = animator

        recyclerView.getItemAnimator()!!.setAddDuration(2000);
        recyclerView.getItemAnimator()!!.setRemoveDuration(2000)
        recyclerView.getItemAnimator()!!.setMoveDuration(1500);
        recyclerView.getItemAnimator()!!.setChangeDuration(2000);

        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is SetONCLick)
        {
            listner=context
            Log.i("listner",(listner is SetONCLick).toString())
        }
        if(listner!=null)
        {
            Log.i("afyuvhbubiu","xtcyuboin")
        }

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun CustomOnClick(stc: StocksJSON) {
        listner!!.Eventclick(stc)
    }



}
