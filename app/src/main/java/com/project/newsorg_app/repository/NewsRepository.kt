package com.project.newsorg_app.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.newsorg_app.database.ArticleDao
import com.project.newsorg_app.database.NewsDatabase
import com.project.newsorg_app.models.Article
import com.project.newsorg_app.models.NewsResponse
import com.project.newsorg_app.network.NewsApiService
import com.project.newsorg_app.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class NewsRepository(
    private val apiService: NewsApiService,
    private val database: NewsDatabase
) {
    private val articleDao = database.articleDao()
    val readArticles: LiveData<List<Article>> = articleDao.getAllReadArticles()

    suspend fun getTopHeadlines(country: String, apiKey: String): Response<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTopHeadlines(country, apiKey)
                if (response.isSuccessful) {
                    Log.d("NewsRepository", "Fetched news successfully")
                } else {
                    Log.e("NewsRepository", "Failed to fetch news: ${response.errorBody()?.string()}")
                }
                response
            } catch (e: Exception) {
                Log.e("NewsRepository", "Error fetching news: ${e.message}")
                throw e
            }
        }
    }


    suspend fun searchNews(query: String, apiKey: String): Response<NewsResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchNews(query, apiKey)
                if (response.isSuccessful) {
                    Log.d("NewsRepository", "Search news successful")
                } else {
                    Log.e("NewsRepository", "Failed to search news: ${response.errorBody()?.string()}")
                }
                response
            } catch (e: Exception) {
                Log.e("NewsRepository", "Error searching news: ${e.message}")
                throw e
            }
        }
    }

    suspend fun insertArticle(article: Article) {
        withContext(Dispatchers.IO) {
            try {
                articleDao.insertArticles(listOf(article)) // Perbaikan di sini
                Log.d("NewsRepository", "Article saved: ${article.title}")
            } catch (e: Exception) {
                Log.e("NewsRepository", "Error saving article: ${e.message}")
            }
        }
    }


    fun getAllReadArticles(): LiveData<List<Article>> {
        return database.articleDao().getAllReadArticles()
    }



}



