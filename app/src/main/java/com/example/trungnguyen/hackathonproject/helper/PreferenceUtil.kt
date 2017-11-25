package com.example.trungnguyen.hackathonproject.helper

import android.content.SharedPreferences
import android.app.Activity
import android.content.Context


/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class PreferenceUtil {

    companion object {
        private fun getSharedPreferenceUtil(context: Context): SharedPreferences =
                context.getSharedPreferences(ConstHelper.APP_NAME, Activity.MODE_PRIVATE)

        fun saveListPatient(context: Context, list: ArrayList<String>) {
            val editor = getSharedPreferenceUtil(context).edit()
            val set = HashSet<String>(list)
            editor.putStringSet(ConstHelper.SAVE_LIST_PATIENT, set)
            editor.apply()
        }

        fun getListPatient(context: Context): Set<String> {
            val preferences = getSharedPreferenceUtil(context)
            return preferences.getStringSet(ConstHelper.SAVE_LIST_PATIENT, HashSet<String>())
        }
    }
}