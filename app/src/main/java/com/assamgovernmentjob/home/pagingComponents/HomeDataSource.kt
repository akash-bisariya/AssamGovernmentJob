package com.assamgovernmentjob.home.pagingComponents

import android.app.Application
import android.arch.paging.PageKeyedDataSource
import android.content.Context
import android.os.Build
import android.util.ArrayMap
import android.util.Log
import com.assamgovernmentjob.R
import com.assamgovernmentjob.api.ApiClient
import com.assamgovernmentjob.api.ApiService
import com.assamgovernmentjob.constants.AppConstants
import com.assamgovernmentjob.home.*
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by akash
 * on 18/5/18.
 */
class HomeDataSource(val mApplication: Application) : PageKeyedDataSource<Long, Link>() {
    private var homeList: List<Link>? = null
    private var totalPage = 0
    private var currentPage: Long = 1
    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Link>) {
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Link>) {
        val retrofit: Retrofit = ApiClient.getClient()
        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
        arrayMap["data"] = "all"
        arrayMap["page"] = "1"
        arrayMap["DeviceToken"] = mApplication.getSharedPreferences(mApplication.resources.getString(R.string.app_name), Context.MODE_PRIVATE).getString("token", "")
        arrayMap["DeviceId"] = mApplication.getSharedPreferences(mApplication.resources.getString(R.string.app_name), Context.MODE_PRIVATE).getString("deviceId", "")
        arrayMap["key"] = AppConstants.Service_Key
        val response = retrofit.create(ApiService::class.java).getHomeData(arrayMap)
        launch {
            response.enqueue(object : Callback<HomeModel> {
                override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {
                    Log.d("assamGovernMentJobs", "Network not available, Please check internet connection")
                }

                override fun onResponse(call: Call<HomeModel>?, response: Response<HomeModel>?) {
                    homeList = response!!.body()!!.homeData.links
                    totalPage = response.body()!!.homeData.totalCount.toInt()
                    callback.onResult(homeList!!, 1, currentPage++)
                }

            })
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Link>) {
        val retrofit: Retrofit = ApiClient.getClient()
        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
        arrayMap["data"] = "all"
        arrayMap["page"] = currentPage.toString()
        arrayMap["DeviceToken"] = ""
        arrayMap["DeviceId"] = ""
        arrayMap["key"] = AppConstants.Service_Key
        val response = retrofit.create(ApiService::class.java).getHomeData(arrayMap)
        launch {
            response.enqueue(object : Callback<HomeModel> {
                override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {
                    Log.d("assamGovernMentJobs", "Network not available, Please check internet connection")
                }

                override fun onResponse(call: Call<HomeModel>?, response: Response<HomeModel>?) {
                    homeList = response!!.body()!!.homeData.links
                    if (currentPage > totalPage) currentPage = 0
                    callback.onResult(homeList!!, if (currentPage > totalPage) currentPage else currentPage++)
                }

            })
        }
    }
}