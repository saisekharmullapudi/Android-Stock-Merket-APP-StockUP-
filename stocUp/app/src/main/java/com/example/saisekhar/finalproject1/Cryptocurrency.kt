package com.example.saisekhar.finalproject1


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
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
class Cryptocurrency : Fragment(),CryptoRecyclerAdapter.CustomOnClickCrypto {
    override fun CustomOnCrypto(stc: CryptoJSON) {
        listner1.EventclickCrypto(stc)
    }

    var num:Int=0
    lateinit var listner1:SetONCLickCrypto
    lateinit var recyclerView1 : RecyclerView
    lateinit var recyclerAdapter1: CryptoRecyclerAdapter
    interface SetONCLickCrypto
    {
        fun EventclickCrypto(data:CryptoJSON)
    }
    companion object {
        fun newInstance():Cryptocurrency
        {
            Cryptocurrency().apply {

            }
            return Cryptocurrency()
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

        val rootView= inflater.inflate(R.layout.fragment_cryptocurrency, container, false)
//        listner = rootView.context as SetONCLick
        recyclerView1= rootView.findViewById(R.id.recViewcrypto)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView1.setLayoutManager(mLayoutManager)
//        cont=container!!.context
        recyclerAdapter1= CryptoRecyclerAdapter(Cryptocurrency())
        recyclerView1.adapter=recyclerAdapter1;
        recyclerAdapter1.setListner(this)
        recyclerView1.itemAnimator = SlideInLeftAnimator()
        val animator = SlideInUpAnimator(OvershootInterpolator(1f))
        recyclerView1.itemAnimator = animator

        recyclerView1.getItemAnimator()!!.setAddDuration(4000);
        recyclerView1.getItemAnimator()!!.setRemoveDuration(2000)
        recyclerView1.getItemAnimator()!!.setMoveDuration(1500);
        recyclerView1.getItemAnimator()!!.setChangeDuration(2000);

        return rootView
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is SetONCLickCrypto)
        {
            listner1=context
        }
        if(listner1!=null)
        {
            Log.i("gsdbcnyufm","heynfd")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }


}

