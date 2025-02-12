package com.project.newsorg_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newsorg_app.models.Article
import com.project.newsorg_app.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    val newsList: LiveData<List<Article>> get() = _newsList
    private val _newsList = MutableLiveData<List<Article>>()

    private val _readArticles = MutableLiveData<List<Article>>()
    val readArticles: LiveData<List<Article>> get() = _readArticles

    fun fetchNews(country: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTopHeadlines(country, apiKey)
                if (response.isSuccessful) {
                    _newsList.postValue(response.body()?.articles ?: emptyList())
                    Log.d("NewsViewModel", "Total Articles: ${response.body()?.articles?.size}")
                } else {
                    Log.e("NewsViewModel", "Failed to fetch news: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Exception fetching news: ${e.message}")
            }
        }
    }

    fun searchNews(query: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchNews(query, apiKey)
                if (response.isSuccessful) {
                    _newsList.postValue(response.body()?.articles ?: emptyList())
                    Log.d("NewsViewModel", "Search results: ${response.body()?.articles?.size}")
                } else {
                    Log.e("NewsViewModel", "Failed to search news: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Exception searching news: ${e.message}")
            }
        }
    }

    fun saveReadArticle(article: Article) {
        viewModelScope.launch {
            repository.insertArticle(article)
            updateReadArticles(article)
        }
    }

    private fun updateReadArticles(article: Article) {
        val currentList = _readArticles.value ?: emptyList()
        val updatedList = listOf(article) + currentList.filter { it.url != article.url }
        _readArticles.postValue(updatedList)
    }
}
