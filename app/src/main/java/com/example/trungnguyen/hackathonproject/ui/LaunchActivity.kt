package com.example.trungnguyen.hackathonproject.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.service.NotificationService

/**
 * Author : Trung Nguyen
 * Date : 11/23/2017
 */

class LaunchActivity : AppCompatActivity() {

    private var mBound = false

    private val mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            val serviceIntent = Intent(this@LaunchActivity, NotificationService::class.java)
            startService(serviceIntent)
            mBound = true
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, NotificationService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
    }
}
