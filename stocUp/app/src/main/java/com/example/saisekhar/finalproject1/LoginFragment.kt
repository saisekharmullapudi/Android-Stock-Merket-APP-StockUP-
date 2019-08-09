package com.example.saisekhar.finalproject1


import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : Fragment() {
    private var listener : OnFragmentInteractionListener ? = null

    override fun onCreate ( savedInstanceState : Bundle ?) {
        super . onCreate ( savedInstanceState )
        arguments ?. let {
        }
    }

    override fun onCreateView ( inflater : LayoutInflater , container : ViewGroup ?, savedInstanceState : Bundle ?) : View ? {
        // Inflate the layout for this fragment
        return inflater . inflate (R. layout . fragment_login , container , false )
    }
    override fun onViewCreated ( view : View , savedInstanceState : Bundle ?) {
        super . onViewCreated (view , savedInstanceState )
        view . background = ColorDrawable (0xFFA500 )
        // button click event handler !
        login_button.setOnClickListener {
            performLogin ()
        }
        // switch to sign up!
        back_to_register . setOnClickListener {
            val email = email_login.text.toString ()
            val password = password_login.text.toString ()
            listener !!. onSignUpRoutine(email, password )
        }

//        /gmail_login.setOnClickListener{
//            Gmail_login()
//        }

    }
    public fun Gmail_login()
    {
        Log.i("Performing Gmail login","in PErform  Gmail Login")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }
    private fun performLogin () {
        Log.i("Performing login","in PErform Login")
        val email = email_login . text . toString ()
        val password = password_login . text . toString ()
        if ( email . isEmpty () || password . isEmpty () ) {
            Toast . makeText ( context , " Please fill out email /pw.", Toast . LENGTH_SHORT ). show ()
            return
        }
        // Firebase Authentication using email and password !
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password ).addOnCompleteListener{
            Log.i("succesful","succesful")
            if ( it. isSuccessful ) {
                Log.d(" Login ", " Successfully logged in:${it. result !!. user . uid }")
                // launch the Main activity , clear back stack ! not going back to login activity when back button pressed
               //
                listener!!.mainactivity()
            }
        }
                . addOnFailureListener {
                    Toast . makeText ( context , " Failed to log in:${it. message }", Toast . LENGTH_SHORT ). show ()
                }
    }
    override fun onAttach ( context : Context) {
        super . onAttach ( context )
        if ( context is OnFragmentInteractionListener ) {
            listener = context
        } else {
            throw RuntimeException ( context . toString () + " must implement OnFragmentInteractionListener ")
        }
    }
    override fun onDetach () {
        super . onDetach ()
        listener = null
    }
    interface OnFragmentInteractionListener {
        fun onSignUpRoutine ( email : String , passwd : String )
        fun mainactivity()
    }


}




