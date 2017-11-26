package com.example.trungnguyen.hackathonproject.helper

import android.util.Log
import com.example.trungnguyen.hackathonproject.api.HealthCareApi
import com.example.trungnguyen.hackathonproject.bean.Patient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class ApiHelper(apiCallback: ApiCallback) {

    companion object {
        private val URL = "http://54.201.74.103/Request_bn.php/"
        private val RELATIVE_URL = "http://54.201.74.103/Request_Relatives.php/"
    }

    private val mCallback = apiCallback

    interface ApiCallback {
        fun onSuccess(call: Call<List<Patient>>?, response: Response<List<Patient>>?)
        fun onFailed()
    }

    fun getDataProcess(url: String) {
        val tempUrl = "?ID=$url"
        val patient = HealthCareApi.getService(URL).getData(tempUrl)
        patient.enqueue(object : Callback<List<Patient>> {
            override fun onFailure(call: Call<List<Patient>>?, t: Throwable?) {
                mCallback.onFailed()
            }

            override fun onResponse(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
                mCallback.onSuccess(call, response)
            }
        })
    }

    fun getDataByUserId(url: String) {
        val tempUrl = "?ID_RELATIVE=$url"
        val patient = HealthCareApi.getService(RELATIVE_URL).getDataByClient(tempUrl)
        patient.enqueue(object : Callback<List<Patient>> {
            override fun onFailure(call: Call<List<Patient>>?, t: Throwable?) {
                Log.v("ApiHelper", t?.message)
                mCallback.onFailed()
            }

            override fun onResponse(call: Call<List<Patient>>?, response: Response<List<Patient>>?) {
                mCallback.onSuccess(call, response)
            }

        })
    }
}