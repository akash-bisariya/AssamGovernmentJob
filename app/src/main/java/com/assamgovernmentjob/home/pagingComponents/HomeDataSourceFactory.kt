package com.assamgovernmentjob.home.pagingComponents

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.assamgovernmentjob.home.Link


/**
 * Created by akash
 * on 18/5/18.
 */
class HomeDataSourceFactory(val mApplication: Application) :DataSource.Factory<Long, Link>{
    val usersDataSourceLiveData = MutableLiveData<HomeDataSource>()

    override fun create(): DataSource<Long, Link> {
        val homeDataSource = HomeDataSource(mApplication)
        usersDataSourceLiveData.postValue(homeDataSource)
        return homeDataSource
    }
}