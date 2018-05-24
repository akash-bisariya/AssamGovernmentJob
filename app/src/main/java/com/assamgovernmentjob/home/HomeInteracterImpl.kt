package com.assamgovernmentjob.home

import android.os.Build
import android.util.ArrayMap
import com.assamgovernmentjob.api.ApiClient
import com.assamgovernmentjob.api.ApiService
import com.assamgovernmentjob.constants.AppConstants.Service_Key
import com.assamgovernmentjob.home.model.CategoryModel
import com.assamgovernmentjob.home.model.HomeModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by akash bisariya on 11-05-2018.
 */
class HomeInteracterImpl(val presenter: HomePresenterImpl) : IHomeInteracter {
    override fun requestDataAPI(category: Int, deviceId: String, token: String) {
        val retrofit: Retrofit = ApiClient.getClient()
        val arrayMap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) ArrayMap<String, String>() else HashMap<String, String>()
        if (category == 0) {
            arrayMap["data"] = "all"
            arrayMap["DeviceToken"] = token
            arrayMap["DeviceId"] = deviceId
        } else arrayMap["category_id"] = category.toString()
        arrayMap["key"] = Service_Key
        if (category != 0) {
            val response = retrofit.create(ApiService::class.java).getCategoryLink(arrayMap)
            launch {
                launch(UI) {
                    response.enqueue(object : Callback<CategoryModel> {
                        override fun onFailure(call: Call<CategoryModel>?, t: Throwable?) {
                            presenter.onResultFail(t.toString())
                        }

                        override fun onResponse(call: Call<CategoryModel>?, response: Response<CategoryModel>?) {
                            presenter.onResultSuccess(response!!.body() as CategoryModel)
                        }
                    })
                }
            }
        } else {
            val response = retrofit.create(ApiService::class.java).getHomeData(arrayMap)
            launch {
                launch(UI) {
                    response.enqueue(object : Callback<HomeModel> {
                        override fun onFailure(call: Call<HomeModel>?, t: Throwable?) {
                            presenter.onResultFail(t.toString())
                        }

                        override fun onResponse(call: Call<HomeModel>?, response: Response<HomeModel>?) {
                            presenter.onResultSuccess(response!!.body() as HomeModel)
                        }

                    })
                }
            }
        }
    }

}