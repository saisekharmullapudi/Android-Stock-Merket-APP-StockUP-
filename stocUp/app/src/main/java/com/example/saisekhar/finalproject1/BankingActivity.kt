package com.example.saisekhar.finalproject1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_banking.*

class BankingActivity : AppCompatActivity(),addmoney.addmoneyinterface,withdrawmoney.withdrawinterface {
    override fun onadd() {
        supportFragmentManager.beginTransaction().replace(R.id.bank_container,addmoney()).commit()
    }

    override fun onchange() {
        supportFragmentManager.beginTransaction().replace(R.id.bank_container,withdrawmoney()).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banking)
        supportFragmentManager.beginTransaction().replace(R.id.bank_container,addmoney()).commit()
    }
}
