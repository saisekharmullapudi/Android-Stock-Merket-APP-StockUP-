package com.example.saisekhar.finalproject1

import android.annotation.TargetApi
import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Rational
import android.view.Display
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.content_help.*
import java.io.IOException
import java.io.InputStream
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.widget.Toast


//import jdk.nashorn.internal.objects.ArrayBufferView.buffer



class help : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        setSupportActionBar(toolbar)
//        helptext.text=""
//        var str1=""
//        StreamReader sr = new StreamReader (assets.Open ("read_asset.txt"))
        var p: InputStream? =null
        p=assets.open("text.txt")
        var sz=p.available()
        var buf:ByteArray= ByteArray(sz)
        p.read(buf)
        p.close()
        val str1 = String(buf)
        helptext.text=str1

        pip_but.setOnClickListener {

            var display:Display=windowManager.defaultDisplay
            var size:Point= Point()
            display.getSize(size)
            var width:Int=size.x;
            var height:Int=size.y

            var aspectratio:Rational= Rational(width,height)
            var pipbuider=PictureInPictureParams.Builder()
            pipbuider.setAspectRatio(aspectratio).build()
            enterPictureInPictureMode(pipbuider.build())


        }

        /*try{
            var p:InputStream = assets.open("text.txt")
            var sz=p.available()
            var buf:ByteArray= ByteArray(sz)
            p.read(buf)
            p.close()
            str1=buf.toString()
            helptext.text=str1


        }catch (ex: IOException)
        {
            ex.printStackTrace()
        }*/
        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            Toast.makeText(Myapp.cont, "You pressed floating action button", Toast.LENGTH_LONG).show()
        }

        setupWindowAnimations();
    }

    @RequiresApi(Build.VERSION_CODES.N)
    public fun setupWindowAnimations(){

//        val slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide)
//        slide.setDuration(5000)
//        val fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade)
//        getWindow().setEnterTransition(fade);
//        window.exitTransition = slide
    }

    public override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        Log.i("PIPmode","inside pip mode")
        if(isInPictureInPictureMode)
        {
            Log.i("PIPmode","inside pip mode")
            app_bar.setExpanded(false,true)
//            app_bar.visibility=View.GONE
        }
        else
        {
            app_bar.setExpanded(true,true)
        }

    }
}
