package com.assamgovernmentjob.home

/**
 * Created by akash bisariya on 10-05-2018.
 */
interface IHomePresenter {
    fun getHomeCategoryData(category: Int)
    fun getHomeData(deviceId:String,token:String)
    fun onDestroy()
    fun onResultSuccess(categoryModel: CategoryModel?)
    fun onResultSuccess(homeModel: HomeModel?)
    fun onResultFail(strError: String)
}