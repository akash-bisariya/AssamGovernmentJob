package com.assamgovernmentjob.home.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by akash
 * on 11/5/18.
 */
class ApiClient {
    companion object {
        fun getClient():Retrofit
        {
            val logging = HttpLoggingInterceptor()
            logging.level=HttpLoggingInterceptor.Level.BODY
            val okHttpClient:OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logging)
            return Retrofit.Builder()
                    .baseUrl(ApiSetting.BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
    }
}