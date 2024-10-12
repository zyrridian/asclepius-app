package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.NewsResponse
import com.dicoding.asclepius.helper.ApiKey
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String = "cancer",
        @Query("sortBy") sortBy: String = "relevancy",
        @Query("apiKey") apiKey: String = ApiKey.API_KEY
    ): NewsResponse

}