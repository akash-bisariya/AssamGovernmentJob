package com.assamgovernmentjob.home

/**
 * Created by akash bisariya on 10-05-2018.
 */
interface IHomeInteracter {
    interface OnFinishedListener {
        fun onResultSuccess(arrNewsUpdates: List<ArticlesItem>)
        fun onResultFail(strError: String)
    }
}