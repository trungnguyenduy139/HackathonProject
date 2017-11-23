package com.example.trungnguyen.hackathonproject.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.IBinder
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.base.App
import com.example.trungnguyen.hackathonproject.bean.Patient
import com.example.trungnguyen.hackathonproject.helper.ApiHelper
import retrofit2.Call
import retrofit2.Response
import java.util.*

/**
 * Author : Trung Nguyen
 * Date : 11/23/2017
 */

class NotificationService : Service(), ApiHelper.ApiCallback {

    override fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
        val data = response?.body()!![0]
        if (data.temperature == "36") {
            pushNotification()
        }
    }

    override fun onFailed() {
    }

    private val mApiHelper = ApiHelper(this)

    private val mTimer = Timer()

    private val mTimerTask = object : TimerTask() {
        override fun run() {
            try {
                mApiHelper.getDataProcess()
            } catch (ignored: Exception) {
            }
        }
    }

    override fun onCreate() {
        mTimer.schedule(mTimerTask, 0, 10000)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int = Service.START_STICKY

    private fun pushNotification() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("RSSPullService")
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(""))
        val pendingIntent = PendingIntent.getActivity(baseContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notifyBuilder = Notification.Builder(App.instance?.applicationContext)
                .setContentTitle("Thông báo")
                .setContentText("Tình trạng sức khỏe đang ở mức cảnh báo")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher)
        val notification = notifyBuilder.build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }

    override fun onBind(intent: Intent): IBinder? = null
}
