package com.example.trungnguyen.hackathonproject.helper

import android.widget.Toast
import com.example.trungnguyen.hackathonproject.base.App

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
object UtilHelper {
    fun showToast(msg: String) {
        Toast.makeText(App.instance?.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}