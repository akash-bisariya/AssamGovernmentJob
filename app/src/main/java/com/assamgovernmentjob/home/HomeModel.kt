package com.assamgovernmentjob.home

/**
 * Created by akash bisariya on 12-05-2018.
 */

data class HomeModel(
    val success: String,
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