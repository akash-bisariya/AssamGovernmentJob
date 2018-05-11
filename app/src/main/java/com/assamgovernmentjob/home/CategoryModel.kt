package com.assamgovernmentjob.home

import com.google.gson.annotations.SerializedName

/**
 * Created by akash
 * on 11/5/18.
 */

data class CategoryModel(
        @SerializedName("success")
    val success: String,
        @SerializedName("userData")
    val userData: UserData
)

data class UserData(
    val Message: String,
    val ResponseCode: String,
    val catData: CatData
)

data class CatData(
        val ID: String,
        val post_author: String,
        val post_date: String,
        val post_date_gmt: String,
        val post_content: List<PostContent>,
        val post_title: String,
        val post_status: String,
        val post_name: String,
        val post_modified: String,
        val post_modified_gmt: String,
        val menu_order: String,
        val post_type: String,
        val comment_count: String
)

data class PostContent(
    val str: String,
    val href: String
)