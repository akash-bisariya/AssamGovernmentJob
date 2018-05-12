package com.assamgovernmentjob.api

import com.assamgovernmentjob.home.CategoryModel
import com.assamgovernmentjob.home.HomeData
import com.assamgovernmentjob.home.HomeModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by akash
 * on 11/5/18.
 */
interface ApiService {
    @POST(ApiSetting.CATEGORY)
    fun getCategoryLink(@Body body: Map<String, String>): Call<CategoryModel>
    @POST(ApiSetting.HOME)
    fun getHomeData(@Body body: Map<String, String>):Call<HomeModel>
}