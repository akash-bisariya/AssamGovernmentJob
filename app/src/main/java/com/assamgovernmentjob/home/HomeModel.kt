package com.assamgovernmentjob.home

import com.google.gson.annotations.SerializedName

/**
 * Created by akash bisariya on 12-05-2018.
 */

data class HomeModel(
        @SerializedName("success")
        val success: String,
        @SerializedName("userData")
        val homeData: HomeData
)

data class HomeData(
        val Message: String,
        val ResponseCode: String,
        val links: List<Link>,
        val page: String,
        val totalCount: String
)

data class Link(
        val str: String,
        val href: String
)