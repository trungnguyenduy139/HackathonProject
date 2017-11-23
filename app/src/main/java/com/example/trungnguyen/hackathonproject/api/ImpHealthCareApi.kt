package com.example.trungnguyen.hackathonproject.api

import com.example.trungnguyen.hackathonproject.bean.Patient
import retrofit2.Call
import retrofit2.http.GET

/**
 * Author : Trung Nguyen
 * Date : 11/23/2017
 */
interface ImpHealthCareApi {
    @GET("?ID=" + HealthCareApi.TEST_PATIENT_ID)
    fun getData(): Call<List<Patient>>
}