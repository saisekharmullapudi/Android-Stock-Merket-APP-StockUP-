package com.example.saisekhar.finalproject1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast

public class mybraodcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("inside broadcast","broadcast receiver")
//        Toast.makeText(Myapp.cont, "inside", Toast.LENGTH_LONG).show()
        if (intent!!.getAction() == ConnectivityManager.CONNECTIVITY_ACTION) {

            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo

            if (networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI &&
                    networkInfo.isConnected) {
                // Wifi is connected
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                val ssid = wifiInfo.ssid

                //  Log.e(TAG, " -- Wifi connected --- " + " SSID " + ssid );
                Toast.makeText(Myapp.cont, "You are now connected to internet", Toast.LENGTH_LONG).show()

            }
        } else if (intent!!.getAction()!!.equals(WifiManager.WIFI_STATE_CHANGED_ACTION, ignoreCase = true)) {
            val wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
            if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                // Log.e(TAG, " ----- Wifi  Disconnected ----- ");
                Toast.makeText(Myapp.cont, "Data disconnected, connect to network to enjoy app", Toast.LENGTH_LONG).show()
            }

        }
    }

}