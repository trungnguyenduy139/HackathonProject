package com.example.trungnguyen.hackathonproject.receiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.base.App
import com.example.trungnguyen.hackathonproject.bean.Patient
import com.example.trungnguyen.hackathonproject.helper.ApiHelper
import com.example.trungnguyen.hackathonproject.ui.LaunchActivity
import retrofit2.Call
import retrofit2.Response


/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class TheReceiver : BroadcastReceiver(), ApiHelper.ApiCallback {

    override fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
        val data = response?.body()!!
        var result = "Trạng thái:"
        data.forEach {
            result += "$result Patient ${data.indexOf(it)}: ${it.LUNG_STATE}\n"
        }
        pushNotification(result)
    }

    override fun onFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mApiHelper = ApiHelper(this)

    override fun onReceive(context: Context?, p1: Intent?) {
        Toast.makeText(context, " Success ", Toast.LENGTH_SHORT).show()
        Log.d("Notification", "The Receiver Successful")
        try {
            mApiHelper.getDataByUserId()
        } catch (ignored: Exception) {
        }
    }

    private fun pushNotification(msg: String) {
        val context = App.instance?.applicationContext
        val intentFilter = IntentFilter()
        intentFilter.addAction("RSSPullService")
        val notificationIntent = Intent(context, LaunchActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notifyBuilder = Notification.Builder(App.instance?.applicationContext)
                .setContentTitle("Cảnh báo")
                .setContentText(msg)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher)
        val notification = notifyBuilder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }
}