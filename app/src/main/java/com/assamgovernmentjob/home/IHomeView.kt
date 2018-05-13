package com.assamgovernmentjob.home

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by akash bisariya on 10-05-2018.
 */
interface IHomeView  {
    fun showProgress()
    fun hideProgress()
    fun setHomeData(categoryModel:CategoryModel?)
    fun setHomeData(homeModel: HomeModel?)
    fun getDataFailed()
}