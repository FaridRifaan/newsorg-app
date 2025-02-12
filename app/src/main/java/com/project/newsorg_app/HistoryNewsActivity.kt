package com.project.newsorg_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.newsorg_app.adapter.HistoryAdapter
import com.project.newsorg_app.adapter.NewsAdapter
import com.project.newsorg_app.database.NewsDatabase
import com.project.newsorg_app.databinding.ActivityHistoryNewsBinding
import com.project.newsorg_app.network.RetrofitInstance
import com.project.newsorg_app.repository.NewsRepository
import com.project.newsorg_app.viewmodel.HistoryViewModel
import com.project.newsorg_app.viewmodel.HistoryViewModelFactory
import com.project.newsorg_app.viewmodel.NewsViewModel
import com.project.newsorg_app.viewmodel.NewsViewModelFactory

class HistoryNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryNewsBinding
    private lateinit var historyAdapter: HistoryAdapter

    private val historyViewModel: HistoryViewModel by viewModels {
        val database = NewsDatabase.getDatabase(this)
        HistoryViewModelFactory(database.articleDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeReadArticles()
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter { article ->
            val intent = Intent(this, DetailNewsActivity::class.java).apply {
                putExtra("ARTICLE_URL", article.url)
            }
            startActivity(intent)
        }

        binding.recyclerViewHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryNewsActivity)
            adapter = historyAdapter
        }
    }

    private fun observeReadArticles() {
        historyViewModel.readArticles.observe(this) { articles ->
            if (articles.isNullOrEmpty()) {
                binding.tvNoHistory.visibility = View.VISIBLE
            } else {
                binding.tvNoHistory.visibility = View.INVISIBLE
                historyAdapter.setNewsList(articles)
            }
        }
    }
}