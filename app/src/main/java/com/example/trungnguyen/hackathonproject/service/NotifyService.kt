package com.example.trungnguyen.hackathonproject.service

import android.app.IntentService
import android.content.Intent
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.example.trungnguyen.hackathonproject.base.App
import com.example.trungnguyen.hackathonproject.helper.ConstHelper
import com.example.trungnguyen.hackathonproject.helper.PreferenceUtil
import com.example.trungnguyen.hackathonproject.receiver.NotifyReceiver

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */

class NotifyService : IntentService("NotifyService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
        }
        startStuff()
    }

    companion object {
        val CREATE = "create"
    }

    private fun startStuff() {
        val intent = Intent(this, NotifyReceiver::class.java)
        intent.putExtra(ConstHelper.PUT_USER_ID, PreferenceUtil.getUserId(App.instance?.applicationContext!!))
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1200, 1200, pendingIntent)
    }
}