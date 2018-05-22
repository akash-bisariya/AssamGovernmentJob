package com.assamgovernmentjob.home.pagingComponents

import android.arch.lifecycle.ViewModel
import android.app.Application
import android.arch.lifecycle.ViewModelProvider


/**
 * Created by akash bisariya on 21-05-2018.
 */
class ViewModelFactory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mApplication) as T
    }
}