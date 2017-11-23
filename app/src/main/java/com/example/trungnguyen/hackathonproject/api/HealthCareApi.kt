package com.example.trungnguyen.hackathonproject.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author : Trung Nguyen
 * Date : 11/23/2017
 */
class HealthCareApi {

    companion object {
        const val TEST_PATIENT_ID = "HK2"
        private val URL = "http://54.201.74.103/Request_bn.php/"
        private var impHealthCareApi: ImpHealthCareApi? = null
        fun getService(): ImpHealthCareApi {
            if (impHealthCareApi == null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                impHealthCareApi = retrofit.create(ImpHealthCareApi::class.java)
            }
            return impHealthCareApi!!
        }
    }
}
