package com.example.saisekhar.finalproject1

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended
import android.net.ConnectivityManager
import android.content.IntentFilter
import android.graphics.Color.parseColor
import android.content.BroadcastReceiver
import android.content.Context
import android.graphics.Color.parseColor
import android.widget.Toast
import android.net.NetworkInfo




class LoginActivity : AppCompatActivity(),LoginFragment.OnFragmentInteractionListener,SignupFragment.OnFragmentInteractionListener,BlankFragment.BlankFragmentInterface {
    override fun mainactivity() {
        var intent:Intent= Intent(this,MainActivity::class.java)
        intent . flags = Intent . FLAG_ACTIVITY_CLEAR_TASK .or( Intent . FLAG_ACTIVITY_NEW_TASK )
        var options: ActivityOptions = ActivityOptions.makeCustomAnimation(this, R.transition.fade_in, R.transition.push_up_out);
        startActivity(intent,options.toBundle())
    }

    override fun Login() {

        var intent:Intent= Intent(this,MainActivity::class.java)
        var options:ActivityOptions =ActivityOptions.makeCustomAnimation(this, R.transition.slide_in_left, R.transition.fade_out);
        startActivity(intent)
    }

    lateinit var frag : Fragment
    @SuppressLint("ResourceType")
    override fun onClickButton(id: Int) {
        if(id==R.id.register)
        {
            var fg:Fragment= supportFragmentManager.findFragmentById(R.id.login_container)!!
            frag=SignupFragment . newInstance ("" , "" )
            supportFragmentManager . beginTransaction () .setCustomAnimations(R.transition.fade_in,R.transition.fade_out,R.transition.fade_in,R.transition.fade_out).replace (R.id.login_container, frag). commit ()
//            var fm= supportFragmentManager
//            var ft:FragmentTransaction=fm.beginTransaction()
//            val fragmentTransactionExtended = FragmentTransactionExtended(this, ft, fg, frag, R.id.login_container)
        }
        else if(id==R.id.signin)
        {
            frag=LoginFragment ()
            supportFragmentManager . beginTransaction () .setCustomAnimations(R.transition.slide_in_left,R.transition.slide_out_right,R.transition.slide_in_left,R.transition.slide_out_right). replace (R.id.login_container,frag).commit ()
        }
    }


    var auth : FirebaseAuth? = null
    @SuppressLint("ResourceType")
    override fun onSignUpRoutine(email: String, passwd: String) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        frag=SignupFragment . newInstance (email , passwd )
        supportFragmentManager . beginTransaction () .setCustomAnimations(R.transition.fade_in,R.transition.fade_out,R.transition.slide_in_left,R.transition.slide_out_right). replace (R.id.login_container,frag ). commit ()
    }

    @SuppressLint("ResourceType")
    override fun onSignInRoutine() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        frag=LoginFragment()
        supportFragmentManager . beginTransaction () .setCustomAnimations(R.transition.push_down_in,R.transition.push_up_out,R.transition.slide_in_left,R.transition.slide_out_right). replace (R.id.login_container,frag).commit ()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(savedInstanceState==null)
        {
            frag= BlankFragment.newInstance()
            supportFragmentManager.beginTransaction().setCustomAnimations(R.transition.fade_in,R.transition.slide_out_right,R.transition.slide_in_left,R.transition.slide_out_right).add(R.id.login_container ,frag). commit ()
        }
        else
        {
            Log.i("rotate","changed")
            frag=supportFragmentManager.getFragment(savedInstanceState,"frag")!!
            supportFragmentManager.beginTransaction().setCustomAnimations(R.transition.slide_in_left,R.transition.slide_out_right,R.transition.slide_in_left,R.transition.slide_out_right).add(R.id.login_container ,frag ). commit ()

        }

//        supportFragmentManager.beginTransaction().add(R.id.login_container ,BlankFragment.newInstance() ). commit ()
        registerNetworkBroadcastReceiver()
    }
    public fun registerNetworkBroadcastReceiver()
    {
        val network = IntentFilter("android.net.wifi.WIFI_STATE_CHANGED")
        registerReceiver(networkChangeReceiver, network);
    }


    public override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState!!,"frag",frag)
    }

    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

                Toast.makeText(Myapp.cont, "network connectivity changed", Toast.LENGTH_LONG).show()

        }
    }
}
