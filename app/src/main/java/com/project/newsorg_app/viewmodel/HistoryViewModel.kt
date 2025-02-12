package com.project.newsorg_app.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.newsorg_app.database.ArticleDao
import com.project.newsorg_app.models.Article
import com.project.newsorg_app.repository.NewsRepository
import kotlinx.coroutines.launch


class HistoryViewModel(private val articleDao: ArticleDao) : ViewModel() {
    val readArticles: LiveData<List<Article>> = articleDao.getAllReadArticles()

}

class HistoryViewModelFactory(private val articleDao: ArticleDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(articleDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
