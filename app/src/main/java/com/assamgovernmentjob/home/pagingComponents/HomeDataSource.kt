package com.assamgovernmentjob.home.pagingComponents

import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.PageKeyedDataSource
import android.os.Build
import android.util.ArrayMap
import com.assamgovernmentjob.api.ApiClient
import com.assamgovernmentjob.api.ApiService
import com.assamgovernmentjob.constants.AppConstants
import com.assamgovernmentjob.home.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by akash
 * on 18/5/18.
 */
class HomeDataSource : PageKeyedDataSource<Long, Link>() {
    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Link>) {
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Link>) {
        val retrofit: Retrofit = ApiClient.getClient()
        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
        arrayMap["data"] = "all"
        arrayMap["page"]="1"
        arrayMap["DeviceToken"] = ""
        arrayMap["DeviceId"] = ""
        arrayMap["key"] = AppConstants.Service_Key
        val response = retrofit.create(ApiService::class.java).getHomeData(arrayMap)
        launch {
            response.enqueue(object : Callback<HomeModel> {
                override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {

                }
                override fun onResponse(call: Call<HomeModel>?, response: Response<HomeModel>?) {
                    homeList= response!!.body()!!.homeData.links
                    callback.onResult(homeList!!,1,2)
                }

            })
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Link>) {
        val retrofit: Retrofit = ApiClient.getClient()
        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
        arrayMap["data"] = "all"
        arrayMap["page"]=params.key.toString()
        arrayMap["DeviceToken"] = ""
        arrayMap["DeviceId"] = ""
        arrayMap["key"] = AppConstants.Service_Key
        val response = retrofit.create(ApiService::class.java).getHomeData(arrayMap)
        launch {
            response.enqueue(object : Callback<HomeModel> {
                override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {

                }
                override fun onResponse(call: Call<HomeModel>?, response: Response<HomeModel>?) {
                    homeList= response!!.body()!!.homeData.links
                    callback.onResult(homeList!!,1)
                }

            })
        }
    }

    private var homeList: List<Link>? = null

//    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Link>) {
//    }
//
//    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Link>) {
//        val retrofit: Retrofit = ApiClient.getClient()
//        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
//        arrayMap["data"] = "all"
//        arrayMap["page"]="1"
//        arrayMap["DeviceToken"] = ""
//        arrayMap["DeviceId"] = ""
//        val response = retrofit.create(ApiService::class.java).getHomeData(arrayMap)
//        launch {
//            response.enqueue(object : Callback<HomeModel> {
//                override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {
//
//                }
//                override fun onResponse(call: Call<HomeModel>?, response: Response<HomeModel>?) {
//                    homeList= response!!.body()!!.homeData.links
//                    callback.onResult(homeList!!)
//                }
//
//            })
//        }
//
//    }
//
//    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Link>) {
//
//    }
//
//    override fun getKey(item: Link): Long {
//        return 1
//    }
}