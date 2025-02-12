package com.project.newsorg_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.newsorg_app.databinding.ItemHistoryBinding
import com.project.newsorg_app.models.Article


class HistoryAdapter(private val onItemClick: (Article) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private val articles = mutableListOf<Article>()

    fun setNewsList(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                binding.textViewTitleHistory.text = article.title
                binding.textViewUrlHistory.text = article.url
                Glide.with(binding.imageViewHistory.context).load(article.urlToImage).into(binding.imageViewHistory)


                root.setOnClickListener { onItemClick(article) }
            }
        }
    }
}
