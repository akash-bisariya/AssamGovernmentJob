package com.assamgovernmentjob.home.api
import com.assamgovernmentjob.home.CategoryModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by akash
 * on 11/5/18.
 */
interface ApiService {
    @POST(ApiSetting.CATEGORY)
    fun getCategoryLink(@Body body: Map<String,String>): Call<CategoryModel>
}