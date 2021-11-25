package com.asuprojects.kotlincustomcomponents.fragments.httprequests.retrofit

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitConfig {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.hgbrasil.com/")
        .addConverterFactory(JacksonConverterFactory.create())
        //.addConverterFactory(GsonConverterFactory.create())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .build()

    fun getMoneyExchangeService(): MoneyExchangeService{
        return this.retrofit.create(MoneyExchangeService::class.java)
    }
}