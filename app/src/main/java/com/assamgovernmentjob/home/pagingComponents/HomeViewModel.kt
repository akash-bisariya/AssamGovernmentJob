package com.assamgovernmentjob.home.pagingComponents

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.assamgovernmentjob.home.Link

/**
 * Created by akash
 * on 18/5/18.
 */
class HomeViewModel(mApplication: Application) : ViewModel() {
    var homeDataList: LiveData<PagedList<Link>>
    private var sourceFactory: HomeDataSourceFactory? = null
    private val pageSize: Int = 15

    init {
        sourceFactory = HomeDataSourceFactory(mApplication)
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(true)
                .setPageSize(pageSize)
                .build()
        homeDataList = LivePagedListBuilder<Long, Link>(sourceFactory!!, config).build()

    }

    fun refresh() {
        sourceFactory!!.usersDataSourceLiveData.value!!.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
    }
}