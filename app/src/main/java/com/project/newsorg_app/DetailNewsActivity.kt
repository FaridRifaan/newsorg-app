package com.project.newsorg_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.project.newsorg_app.databinding.ActivityDetailNewsBinding
import com.project.newsorg_app.databinding.ActivityMainBinding
import com.project.newsorg_app.models.Article

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val articleUrl = intent.getStringExtra("ARTICLE_URL")

        binding.webView.apply {
            webViewClient = WebViewClient()
            if (articleUrl != null) {
                loadUrl(articleUrl)
            }
        }

    }


}