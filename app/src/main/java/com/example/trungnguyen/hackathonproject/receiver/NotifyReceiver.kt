package com.example.trungnguyen.hackathonproject.receiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.IntentFilter
import com.example.trungnguyen.hackathonproject.R
import com.example.trungnguyen.hackathonproject.base.App
import com.example.trungnguyen.hackathonproject.bean.Patient
import com.example.trungnguyen.hackathonproject.helper.ApiHelper
import com.example.trungnguyen.hackathonproject.helper.ConstHelper
import com.example.trungnguyen.hackathonproject.helper.PreferenceUtil
import com.example.trungnguyen.hackathonproject.helper.UtilHelper
import com.example.trungnguyen.hackathonproject.ui.PatientDialog
import retrofit2.Call
import retrofit2.Response

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class NotifyReceiver : BroadcastReceiver(), ApiHelper.ApiCallback {

    private val mWarningPatients = ArrayList<String>()
    private var mContext: Context? = null

    override fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
        val data = response?.body()!!
        val resultBuilder = StringBuilder()
        mWarningPatients.clear()
        data.forEach {
            if (it.state == "0") mWarningPatients.add(it.ID)
            resultBuilder.append(it.ID).append("\n")
        }
        PreferenceUtil.saveLatestWarning(mContext!!, mWarningPatients)
        pushNotification("Cảnh báo: $resultBuilder")
    }

    override fun onFailed() {
        UtilHelper.showToast("Xẩy ra lỗi truy cập dữ liệu")
    }

    private val mApiHelper = ApiHelper(this)

    override fun onReceive(context: Context?, intent: Intent?) {
        mContext = context
        UtilHelper.showToast("Success")
        try {
            mApiHelper.getDataByUserId(intent?.getStringExtra(ConstHelper.PUT_USER_ID)!!)
        } catch (ignored: Exception) {
        }
    }

    private fun pushNotification(msg: String) {
        val context = App.instance?.applicationContext
        val intentFilter = IntentFilter()
        intentFilter.addAction("RSSPullService")
        val notificationIntent = Intent(context, PatientDialog::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        notificationIntent.putExtra(ConstHelper.PATIENT_LIST, mWarningPatients).putExtra(ConstHelper.IS_WARNING, true)
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