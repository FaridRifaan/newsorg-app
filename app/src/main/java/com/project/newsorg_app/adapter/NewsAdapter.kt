package com.project.newsorg_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsorg_app.R
import com.project.newsorg_app.databinding.ItemNewsBinding
import com.project.newsorg_app.models.Article



class NewsAdapter(private val onItemClick: (Article) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: List<Article> = emptyList()

    fun setNewsList(news: List<Article>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.titleTextView.text = article.title
            binding.nameNews.text = article.source.name
            binding.authorTv.text = article.author
            binding.publishTextView.text = article.publishedAt

            Glide.with(binding.newsImageView.context).load(article.urlToImage).into(binding.newsImageView)

            itemView.setOnClickListener {
                onItemClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount() = newsList.size
}





