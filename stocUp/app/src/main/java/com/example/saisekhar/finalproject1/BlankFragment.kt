package com.example.saisekhar.finalproject1


import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_blank.*
import android.graphics.drawable.AnimationDrawable




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BlankFragment : Fragment() {
    private var listener : BlankFragmentInterface?= null

    companion object {
        fun newInstance():BlankFragment
        {
            BlankFragment().apply {
            }
            return BlankFragment()
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
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }
    override fun onViewCreated ( view : View , savedInstanceState : Bundle ?) {
        super . onViewCreated (view , savedInstanceState )
        val animation = AnimationDrawable()
        animation.addFrame(activity!!.getDrawable(R.drawable.logo),1000);
        animation.addFrame(activity!!.getDrawable(R.drawable.stocks),800);

//        anim_img.setBackgroundDrawable(animation)
        anim_img.background=animation
        animation.start()
        view . background = ColorDrawable (0xFFA500 )
        signin.setOnClickListener{
            listener!!.onClickButton(R.id.signin)
        }
        register.setOnClickListener{
            listener!!.onClickButton(R.id.register)
        }


    }

    interface BlankFragmentInterface {
        fun onClickButton ( id:Int )
    }
    override fun onAttach ( context : Context) {
        super . onAttach ( context )
        if ( context is BlankFragmentInterface ) {
            listener = context
        } else {
            throw RuntimeException ( context . toString () + " must implement OnFragmentInteractionListener ")
        }
    }
    override fun onDetach () {
        super . onDetach ()
        listener = null
    }


}
