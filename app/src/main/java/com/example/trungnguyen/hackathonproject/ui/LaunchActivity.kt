package com.example.trungnguyen.hackathonproject.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.trungnguyen.hackathonproject.R
import android.content.Context
import android.view.View
import android.widget.Button
//import android.os.SystemClock
//import android.app.AlarmManager
//import android.app.PendingIntent
//import com.example.trungnguyen.hackathonproject.receiver.RestartServiceReceiver
import com.example.trungnguyen.hackathonproject.service.NotifyService
import android.text.TextUtils
import android.provider.Settings.SettingNotFoundException
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AlertDialog


/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        if (view?.id == R.id.bt_nearest) {
            checkForNearestHospital()
        }
    }

    private fun checkForNearestHospital() {
        if (isLocationEnabled(this)) {

        } else buildAlertMessageNoGps()
    }

    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("GPS của thiết bị đã bị tắt, vui lòng mở lại!")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", { _, _ ->
                    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                })
                .setNegativeButton("Hủy", { dialog, _ -> dialog.dismiss() })
        val alert = builder.create()
        alert.show()
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationMode: Int
        val locationProviders: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
            } catch (ignored: SettingNotFoundException) {
                return false
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF

        } else {
            locationProviders = Settings.Secure.getString(context.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            return !TextUtils.isEmpty(locationProviders)
        }


    }

//    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
//        mContext = this
//        val alarm = Intent(this.mContext, RestartServiceReceiver::class.java)
//        val alarmRunning = PendingIntent.getBroadcast(this.mContext, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null
//        if (!alarmRunning) {
//            val pendingIntent = PendingIntent.getBroadcast(this.mContext, 0, alarm, 0)
//            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 10000, pendingIntent)
//        }
        findViewById<Button>(R.id.bt_nearest).setOnClickListener(this)
        val msgIntent = Intent(this, NotifyService::class.java)
        startService(msgIntent)
    }
}
