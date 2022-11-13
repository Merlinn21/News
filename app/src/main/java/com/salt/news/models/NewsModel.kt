package com.salt.news.models

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
data class NewsModel(
    @SerializedName("source")
    val source: SourceModel,

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("publishedAt")
    val publishedAt: String,

    @SerializedName("content")
    val content: String?
) : java.io.Serializable
