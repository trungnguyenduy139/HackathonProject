package com.example.trungnguyen.hackathonproject.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.trungnguyen.hackathonproject.service.NotifyService


/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class OnOffReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        val service = Intent(context, NotifyService::class.java)
        service.action = NotifyService.CREATE
        context?.startService(service)
    }
}