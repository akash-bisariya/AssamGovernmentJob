package com.assamgovernmentjob.home

import android.os.Build
import android.util.ArrayMap
import android.util.Log
import android.widget.Toast
import com.assamgovernmentjob.api.ApiClient
import com.assamgovernmentjob.api.ApiService
import com.assamgovernmentjob.constants.AppConstants.Service_Key
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.logging.Logger

/**
 * Created by akash bisariya on 11-05-2018.
 */
class HomeInteracterImpl : IHomeInteracter {
    override fun requestDataAPI(onFinishedListener: IHomeInteracter.OnFinishedListener, category: Int) {
        val retrofit: Retrofit = ApiClient.getClient()
        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
        if(category==0) arrayMap["data"]="all" else arrayMap["category_id"] = category.toString()
        arrayMap["key"] = Service_Key
        if(category!=0) {
            val response = retrofit.create(ApiService::class.java).getCategoryLink(arrayMap)
            launch {
                launch(UI) {
                    response.enqueue(object : Callback<CategoryModel> {
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
        else
        {
            val response = retrofit.create(ApiService::class.java).getHomeData(arrayMap)
            launch {
                launch(UI) {
                    response.enqueue(object : Callback<HomeModel> {
                        override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {
                            onFinishedListener.onResultFail(t.toString())
                        }

                        override fun onResponse(call: Call<HomeModel>?, response: Response<HomeModel>?) {
//                            onFinishedListener.onResultSuccess(response as CategoryModel)
                            Log.d("","")
                        }

                    })
                }
            }
        }
    }
}