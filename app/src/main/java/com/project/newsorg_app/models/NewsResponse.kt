package com.project.newsorg_app.models

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status")
    val status: String,  // Status response (misal: "ok")

    @SerializedName("totalResults")
    val totalResults: Int,  // Total berita yang tersedia

    @SerializedName("articles")
    val articles: List<Article>  // List artikel berita
)
