package com.salt.news.models

import com.google.gson.annotations.SerializedName

data class HeadlinesModel(
    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val total: Int,

    @SerializedName("articles")
    val news: List<NewsModel>,
) : java.io.Serializable
