package com.example.trungnguyen.hackathonproject.helper

import android.content.Context
import android.net.ConnectivityManager
import com.example.trungnguyen.hackathonproject.base.App

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
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
