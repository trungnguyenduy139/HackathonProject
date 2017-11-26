package com.example.trungnguyen.hackathonproject.api

import com.example.trungnguyen.hackathonproject.bean.Patient
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */
interface ImpHealthCareApi {
    @GET
    fun getData(@Url url: String): Call<List<Patient>>

    @GET
    fun getDataByClient(@Url url: String): Call<List<Patient>>
}