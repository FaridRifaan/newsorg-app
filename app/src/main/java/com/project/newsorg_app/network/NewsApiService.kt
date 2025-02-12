package com.project.newsorg_app.network

import com.project.newsorg_app.models.NewsResponse
import com.project.newsorg_app.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",  // Pastikan parameter ini ada
        @Query("apiKey") apiKey: String = API_KEY
    ):Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>


}