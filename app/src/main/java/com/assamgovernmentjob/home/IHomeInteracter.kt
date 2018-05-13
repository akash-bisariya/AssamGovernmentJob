package com.assamgovernmentjob.home

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by akash bisariya on 10-05-2018.
 */
interface IHomeInteracter {
    fun requestDataAPI(onFinishedListener: OnFinishedListener, category: Int)
    interface OnFinishedListener {
        fun onResultSuccess(categoryModel: CategoryModel?)
        fun onResultSuccess(homeModel: HomeModel?)
        fun onResultFail(strError: String)
    }
}