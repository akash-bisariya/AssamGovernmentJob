package com.assamgovernmentjob.home

import android.view.MotionEvent
import android.view.View

/**
 * Created by akash
 * on 27/2/18.
 */
interface IOnRecycleItemClick {
    fun onRecycleItemClick(view: View?, position:Int , isCategory:Boolean)
}