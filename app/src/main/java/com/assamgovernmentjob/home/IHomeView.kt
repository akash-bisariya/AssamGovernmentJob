package com.assamgovernmentjob.home

import com.assamgovernmentjob.home.model.CategoryModel
import com.assamgovernmentjob.home.model.HomeModel

/**
 * Created by akash bisariya on 10-05-2018.
 */
interface IHomeView  {
    fun showProgress()
    fun hideProgress()
    fun setHomeData(categoryModel: CategoryModel?)
    fun setHomeData(homeModel: HomeModel?)
    fun getDataFailed()
}