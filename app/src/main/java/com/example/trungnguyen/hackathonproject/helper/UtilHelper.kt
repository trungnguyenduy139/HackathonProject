package com.example.trungnguyen.hackathonproject.helper

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.text.TextUtils
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

    fun isLocationEnabled(context: Context): Boolean {
        val locationMode: Int
        val locationProviders: String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
            } catch (ignored: Exception) {
                return false
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF

        } else {
            locationProviders = Settings.Secure.getString(context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            return !TextUtils.isEmpty(locationProviders)
        }
    }

    fun buildAlertMessageNoGps(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("GPS của thiết bị đã bị tắt, vui lòng mở lại!")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", { _, _ ->
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                })
                .setNegativeButton("Hủy", { dialog, _ -> dialog.dismiss() })
        val alert = builder.create()
        alert.show()
    }

}