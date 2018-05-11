package com.assamgovernmentjob.home

import android.os.Build
import android.util.ArrayMap
import com.assamgovernmentjob.home.api.ApiClient
import com.assamgovernmentjob.home.api.ApiService
import com.assamgovernmentjob.home.constants.AppConstants.Service_Key
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by akash bisariya on 11-05-2018.
 */
class HomeInteracterImpl : IHomeInteracter {
    override fun requestNewsDataAPI(onFinishedListener: IHomeInteracter.OnFinishedListener,category: Int) {
        val retrofit: Retrofit = ApiClient.getClient()
        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
        arrayMap["category_id"] = category.toString()
        arrayMap["key"] = Service_Key
        launch {
            val response = retrofit.create(ApiService::class.java).getCategoryLink(arrayMap)
            launch(UI) {
                  response.enqueue(object : Callback<CategoryModel>
                  {
                      override fun onFailure(call: Call<CategoryModel>?, t: Throwable?) {
                          onFinishedListener.onResultFail(t.toString())
                      }

                      override fun onResponse(call: Call<CategoryModel>?, response: Response<CategoryModel>?) {
                          onFinishedListener.onResultSuccess(response!!.body())
                      }

                  })
            }
        }
    }
}