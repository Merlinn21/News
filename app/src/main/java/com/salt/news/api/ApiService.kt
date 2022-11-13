package com.salt.news.api

import com.salt.news.models.HeadlinesModel
import com.salt.news.models.NewsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?")
    fun getHeadlines(
        @Query("apiKey") api_key : String,
        @Query("country") country : String?,
        @Query("pageSize") pageSize : Int,
        @Query("page") page : Int,
        @Query("category") category : String?
    ) : Call<HeadlinesModel>
}