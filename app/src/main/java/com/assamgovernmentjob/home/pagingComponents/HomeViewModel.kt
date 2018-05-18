package com.assamgovernmentjob.home.pagingComponents

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.assamgovernmentjob.home.HomeData
import com.assamgovernmentjob.home.Link

/**
 * Created by akash
 * on 18/5/18.
 */
class HomeViewModel:ViewModel(){
    var homeDataList:LiveData<PagedList<Link>>
    private var sourceFactory: HomeDataSourceFactory? = null
    private val pageSize:Int = 15
    init {
        sourceFactory = HomeDataSourceFactory()
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(pageSize*2)
                .setEnablePlaceholders(false)
                .setPageSize(pageSize)
                .build()
        homeDataList = LivePagedListBuilder<Long,Link>(sourceFactory!!,config).build()

    }
}