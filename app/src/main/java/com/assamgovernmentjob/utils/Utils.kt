package com.assamgovernmentjob.utils

import android.content.Context
import java.text.SimpleDateFormat
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

        fun formatDate(date: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val outputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss")
            return outputFormat.format(dateFormat.parse(date))
        }
    }
}