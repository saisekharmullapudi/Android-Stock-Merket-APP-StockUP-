package com.example.saisekhar.finalproject1

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log


public class MyFragmentStatePagerAdapter(frag: FragmentManager,str:ArrayList<String>) : FragmentStatePagerAdapter(frag){

    interface StatePageInterface{
        fun transfer(da:StocksJSON)
    }
    var types=str;
    override fun getItem(p0: Int):Fragment {
        var mFrag:Fragment?=null
        if(p0==0)
        {
            mFrag= Stocks.newInstance()
        }
        else if(p0==1)
        {
            mFrag=Cryptocurrency.newInstance()
        }
        return mFrag!!
    }


    override fun getCount(): Int {
        return types.size;
    }

    override fun getPageTitle(p0: Int): CharSequence? {

//        Log.d("pos",posterTable.get(xy[1].title)!!.toString())
//        return xy[position].title.toString()
        return types[p0];
    }

}
