package com.example.saisekhar.finalproject1

import android.app.Application
import android.content.Context

class Myapp: Application() {
    companion object {
        lateinit var cont:Context
        fun getContext():Context{
            return Myapp.cont
        }
    }


    override fun onCreate() {
        super.onCreate()
        cont=applicationContext
    }

}