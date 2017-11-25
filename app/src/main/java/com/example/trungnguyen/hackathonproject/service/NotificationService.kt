package com.example.trungnguyen.hackathonproject.service

import android.app.*
import android.content.Context
import android.content.Intent
//import android.content.IntentFilter
//import android.net.Uri
import android.os.IBinder
//import com.example.trungnguyen.hackathonproject.R
//import com.example.trungnguyen.hackathonproject.base.App
import com.example.trungnguyen.hackathonproject.bean.Patient
import com.example.trungnguyen.hackathonproject.helper.ApiHelper
import retrofit2.Call
import retrofit2.Response


/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */

class NotificationService : Service(), ApiHelper.ApiCallback {

    override fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
        val data = response?.body()!![0]
        if (data.temperature == "36") {
//            pushNotification()
        }
        stopSelf()
    }

    override fun onFailed() {
        stopSelf()
    }

    private val mApiHelper = ApiHelper(this)

    private lateinit var mContext: Context
    private var mBackgroundThread: Thread? = null

    private var mIsRunning = false

    override fun onDestroy() {
        super.onDestroy()
        mIsRunning = false
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        mIsRunning = false
        mBackgroundThread = Thread(mBackgroundTask)
    }

    private val mBackgroundTask = Runnable {
        try {
//            mApiHelper.getDataProcess()
        } catch (ignored: Exception) {
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (!this.mIsRunning) {
            this.mIsRunning = true
            mBackgroundThread?.start()
        }
        return Service.START_STICKY
    }

//    private fun pushNotification() {
//        val intentFilter = IntentFilter()
//        intentFilter.addAction("RSSPullService")
//        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(""))
//        val pendingIntent = PendingIntent.getActivity(baseContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val notifyBuilder = Notification.Builder(App.instance?.applicationContext)
//                .setContentTitle("Thông báo")
//                .setContentText("Tình trạng sức khỏe đang ở mức cảnh báo")
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_SOUND)
//                .setSmallIcon(R.mipmap.ic_launcher)
//        val notification = notifyBuilder.build()
//        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(1, notification)
//    }

    override fun onBind(intent: Intent): IBinder? = null
}
