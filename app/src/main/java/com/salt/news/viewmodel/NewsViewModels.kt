package com.salt.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salt.news.api.RetrofitInstance
import com.salt.news.global.Api
import com.salt.news.global.Setting
import com.salt.news.models.HeadlinesModel
import com.salt.news.models.NewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModels:  ViewModel() {
    private var newsLiveData = MutableLiveData<HeadlinesModel>()

    fun getHeadlines(country : String?, page : Int, category : String?){
        RetrofitInstance.apiService.getHeadlines(Api.API_KEY, country, Setting.PAGE_SIZE, page, category).enqueue(object :
            Callback<HeadlinesModel> {
            override fun onResponse(call: Call<HeadlinesModel>, response: Response<HeadlinesModel>) {
                if (response.body() != null){
                    newsLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<HeadlinesModel>, t: Throwable) {
                println("OnFail: ${t.localizedMessage}")
            }
        })
    }

    fun observeNewsData() : LiveData<HeadlinesModel> {
        return newsLiveData
    }
}