package com.assamgovernmentjob.home.pagingComponents

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.assamgovernmentjob.home.HomeData
import com.assamgovernmentjob.home.Link


/**
 * Created by akash
 * on 18/5/18.
 */
class HomeDataSourceFactory :DataSource.Factory<Long, Link>{
    private val usersDataSourceLiveData = MutableLiveData<HomeDataSource>()

    override fun create(): DataSource<Long, Link> {
        val homeDataSource = HomeDataSource()
        usersDataSourceLiveData.postValue(homeDataSource)
        return homeDataSource
    }
}