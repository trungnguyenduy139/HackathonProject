package com.example.trungnguyen.hackathonproject.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
class HealthCareApi {

    companion object {
        private var mImpHealthCareApi: ImpHealthCareApi? = null
        fun getService(url: String): ImpHealthCareApi {
            if (mImpHealthCareApi == null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                mImpHealthCareApi = retrofit.create(ImpHealthCareApi::class.java)
            }
            return mImpHealthCareApi!!
        }
    }
}
