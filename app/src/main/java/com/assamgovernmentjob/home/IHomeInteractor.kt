package com.assamgovernmentjob.home

import retrofit2.Response

/**
 * Created by akash bisariya on 10-05-2018.
 */
interface IHomeInteractor {
    fun requestNewsDataAPI(onFinishedListener: OnFinishedListener)
    interface OnFinishedListener {
        fun onResultSuccess(arrNewsUpdates: List<Response<Any>>)
        fun onResultFail(strError: String)
    }
}