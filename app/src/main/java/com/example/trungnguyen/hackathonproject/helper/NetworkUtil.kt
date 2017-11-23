package com.example.trungnguyen.hackathonproject.helper

import android.content.Context
import android.net.ConnectivityManager
import com.example.trungnguyen.hackathonproject.base.App

/**
 * Created by Trung Nguyen on 3/17/2017.
 */
object NetworkUtil {
    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = App.instance!!.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}
