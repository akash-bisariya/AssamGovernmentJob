package com.assamgovernmentjob.home

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by akash bisariya on 10-05-2018.
 */
interface IHomeInteracter {
    fun requestDataAPI(category: Int,deviceId:String,token:String)
}