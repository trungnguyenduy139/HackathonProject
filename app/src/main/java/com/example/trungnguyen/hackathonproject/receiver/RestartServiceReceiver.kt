package com.example.trungnguyen.hackathonproject.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.trungnguyen.hackathonproject.service.NotificationService

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class RestartServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val restartIntent = Intent(context, NotificationService::class.java)
        context?.startService(restartIntent)
    }
}