package com.assamgovernmentjob.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by akash
 * on 17/5/18.
 */
class Utils {
    companion object {
        fun isOnline(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
}