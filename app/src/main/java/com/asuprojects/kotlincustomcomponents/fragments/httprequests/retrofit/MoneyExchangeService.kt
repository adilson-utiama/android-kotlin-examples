package com.asuprojects.kotlincustomcomponents.fragments.httprequests.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoneyExchangeService {

    //format=json&key=1abc188b
    @GET("finance?")
    fun requestData(@Query("format") format: String, @Query("key") key: String): Call<ResponseBody>
}