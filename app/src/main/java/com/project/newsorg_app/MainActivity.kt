package com.project.newsorg_app


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.newsorg_app.adapter.NewsAdapter
import com.project.newsorg_app.databinding.ActivityMainBinding
import com.project.newsorg_app.utils.Constants.Companion.API_KEY
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.newsorg_app.database.NewsDatabase
import com.project.newsorg_app.models.Article
import com.project.newsorg_app.network.NewsApiService
import com.project.newsorg_app.network.RetrofitInstance
import com.project.newsorg_app.repository.NewsRepository
import com.project.newsorg_app.viewmodel.NewsViewModel
import com.project.newsorg_app.viewmodel.NewsViewModelFactory
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter.Companion.factory


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepository(RetrofitInstance.api, NewsDatabase.getDatabase(this)))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeNews()
        observeReadArticles()
        setupSearchView()
        setupHistoryButton()

        newsViewModel.fetchNews("us", API_KEY)


    }

    private fun setupHistoryButton() {
        binding.fabButton.setOnClickListener{
            val intent = Intent(this, HistoryNewsActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setupRecyclerView() {
        adapter = NewsAdapter { article ->
            val intent = Intent(this, DetailNewsActivity::class.java).apply {
                putExtra("ARTICLE_URL", article.url)
            }
            startActivity(intent)


            newsViewModel.saveReadArticle(article)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }




    private fun observeReadArticles() {
        newsViewModel.readArticles.observe(this, Observer { articles ->
            Log.d("MainActivity", "Read Articles: ${articles.size}")
            if (articles.isNotEmpty()) {
                adapter.setNewsList(articles)
            }
        })
    }



    private fun observeNews() {
        newsViewModel.newsList.observe(this, Observer { articles ->
            Log.d("MainActivity", "Total Articles in ViewModel: ${articles.size}")
            if (articles != null && articles.isNotEmpty()) {
                adapter.setNewsList(articles)
                Log.d("MainActivity", "Adapter updated with ${articles.size} items.")
                binding.recyclerView.visibility = View.VISIBLE
                binding.shimmerLayout.visibility = View.GONE
            } else {
                Log.d("MainActivity", "Data kosong di ViewModel!")
                binding.recyclerView.visibility = View.GONE
                binding.shimmerLayout.visibility = View.VISIBLE
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    newsViewModel.searchNews(query, API_KEY)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }


}