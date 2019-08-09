package com.example.saisekhar.finalproject1

import android.annotation.TargetApi
import android.app.ActionBar
import android.app.ActivityOptions
import android.app.PictureInPictureParams
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Color
import android.graphics.Point
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.Slide
import android.transition.TransitionInflater
import android.util.Log
import android.util.Rational
import android.view.Display
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.saisekhar.finalproject1.R.drawable.ic_nav_line
import com.example.saisekhar.finalproject1.R.id.toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,Details.DetailTrs {
    override fun function(view: View,tp: Transaction) {
        var intent:Intent= Intent(this,Sell::class.java)
        intent.putExtra("stock_data",tp)
        var name:TextView=view.findViewById(R.id.trans_compaany)
        var options:ActivityOptionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(this,name,"transitionname")
        startActivity(intent,options.toBundle())
    }
    var uid:String?=null
    var person:Person?=null

    val getVal = object : ValueEventListener
    {
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {
            person= p0.getValue<Person>(Person::class.java)
//            Log.i("person name",person!!.firstname.toString()+" "+person!!.lastname.toString())
//            person_name.text=person!!.firstname+" "+person!!.lastname
//            person_mail.text=person!!.email
//            Picasso.get().load(person!!.imgurl).fit().into ( person_img)
//            Log.i("person",person.toString())
//                per= JSONObject(person)
//            Log.i("person",person!!.username.toString())
//            person_name.text="Sekhar Mullapudi"
//            person_mail.text="sekharm@gmail.com"
//            person_img.setImageResource(R.drawable.sky)
//            Picasso.get().load(person!!.profileImageUrl ).fit().into ( mypic )

        }

    }


    init {
        uid = FirebaseAuth . getInstance () . uid ?: ""
        val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().reference
        val mRef = mDatabase.child("users").child(uid!!)
        mRef.addValueEventListener(getVal)
    }
    lateinit var appBar:android.support.v7.app.ActionBar
    lateinit var fragment : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
        var toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar (findViewById(R.id.toolbar))
        appBar = this!!.supportActionBar!!
//        toolbar.setNavigationIcon(R.drawable.ic_nav_line)
        appBar.setLogo(R.drawable.ic_money)



        val fm = supportFragmentManager
        fragment = Details.newInstance()
        Log.i("abc","asdfghjklqwertyuiop")
        val fT = fm.beginTransaction();

        fT.replace(R.id.dummy,fragment)
        // fT.addToBackStack("aboutme container")
        fT.commit()
//        toolbar.navigationIcon!!.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)

        appBar!!.setDisplayShowTitleEnabled(false)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        toggle.setHomeAsUpIndicator(R.drawable.ic_money)
        toggle.isDrawerIndicatorEnabled=false
        toggle.setToolbarNavigationClickListener (View.OnClickListener {
           drawer_layout.openDrawer(GravityCompat.START)
        } )
        toggle.setHomeAsUpIndicator(R.drawable.ic_dehaze)
        drawer_layout.addDrawerListener(toggle)


        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        setupWindowAnimations();
    }
    public fun setupWindowAnimations(){
//        val slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide)
//        slide.setDuration(5000)
//        window.enterTransition=slide
//        window.exitTransition = slide
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
////                enterPictureInPictureMode()
//                var display: Display =windowManager.defaultDisplay
//            var size:Point= Point()
//            display.getSize(size)
//            var width:Int=size.x;
//            var height:Int=size.y
//
//            var aspectratio:Rational= Rational(width,height)
//            var pipbuider= PictureInPictureParams.Builder()
//            pipbuider.setAspectRatio(aspectratio).build()
//            enterPictureInPictureMode(pipbuider.build())
                return true
            }
            R.id.renew->{
                val intent = intent
                var options:ActivityOptions =ActivityOptions.makeCustomAnimation(this, R.transition.fade_in, R.transition.fade_out);
                finish()
                startActivity(intent,options.toBundle())
                return true
            }
//            R.id.refer->
//            {
////                var ls= ArrayList<String>()
////                var cr:ContentResolver=contentResolver
////                val c = cr.query(Telephony.Sms.Inbox.CONTENT_URI, // Official CONTENT_URI from docs
////                        arrayOf(Telephony.Sms.Inbox.BODY),
////                        null, null,
////                        Telephony.Sms.Inbox.DEFAULT_SORT_ORDER)// Select body text
////                // Default sort order
////                val totalSMS = c.count
////                Log.i("count messages",totalSMS.toString())
//                return true
//            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if(isInPictureInPictureMode)
        {
            appBar.hide()
        }
        else
        {
            appBar.show()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.signout-> {
                // Handle the camera action
                var intent2= Intent(this,LoginActivity::class.java)
                intent . flags = Intent . FLAG_ACTIVITY_CLEAR_TASK .or( Intent . FLAG_ACTIVITY_NEW_TASK )
                var options:ActivityOptions =ActivityOptions.makeCustomAnimation(this, R.transition.slide_in_left, R.transition.fade_out)
                this.startActivity ( intent2,options.toBundle())
            }
            R.id.banking -> {
                var intent=Intent(this,BankingActivity::class.java)

                var options:ActivityOptions =ActivityOptions.makeCustomAnimation(this, R.transition.push_down_in, R.transition.push_up_out);
                this.startActivity(intent,options.toBundle())

            }
            R.id.market -> {
                var intent2= Intent(this,Markets::class.java)
                intent . flags = Intent . FLAG_ACTIVITY_CLEAR_TASK .or( Intent . FLAG_ACTIVITY_NEW_TASK )
                var options:ActivityOptions =ActivityOptions.makeCustomAnimation(this, R.transition.slide_in_left, R.transition.slide_out_right);
                startActivity ( intent2 ,options.toBundle())

            }
            /*R.id.scheduler-> {

            }
            R.id.setting -> {

            }*/
            R.id.flip->{
                var intent=Intent(this,flipbook::class.java)
                startActivity(intent)
            }

            R.id.help -> {
                var intent = Intent(this,help::class.java)
                var options:ActivityOptions =ActivityOptions.makeCustomAnimation(this, R.transition.fade_in, R.transition.push_up_out);
                startActivity(intent,options.toBundle())

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
