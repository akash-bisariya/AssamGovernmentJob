package com.assamgovernmentjob.api

import com.assamgovernmentjob.home.model.CategoryModel
import com.assamgovernmentjob.home.model.HomeModel
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