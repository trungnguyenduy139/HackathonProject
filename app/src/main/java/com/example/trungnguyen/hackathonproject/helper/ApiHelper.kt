package com.example.trungnguyen.hackathonproject.helper

import android.content.Context
import com.example.trungnguyen.hackathonproject.api.HealthCareApi
import com.example.trungnguyen.hackathonproject.bean.Patient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author : Trung Nguyen
 * Date : 11/24/2017
 */
class ApiHelper(context: Context) {

    private val mCallback = context as ApiCallback

    interface ApiCallback {
        fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?)
        fun onFailed()
    }

    fun getDataProcess() {
        val patient = HealthCareApi.getService().getData()
        patient.enqueue(object : Callback<List<Patient>> {
            override fun onFailure(call: Call<List<Patient>>?, t: Throwable?) {
                mCallback.onFailed()
            }

            override fun onResponse(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
                mCallback.onSuccess(call, response)
            }
        })
    }
}