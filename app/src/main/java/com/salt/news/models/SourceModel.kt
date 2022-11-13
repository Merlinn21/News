package com.salt.news.models

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class SourceModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,
) : java.io.Serializable
