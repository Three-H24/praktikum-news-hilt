package com.example.praktikum_news_hilt.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.praktikum_news_hilt.data.model.Article
import com.example.praktikum_news_hilt.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
                tvSource.text = article.source?.name
                Glide.with(ivArticelImage.context).load(article.urlToImage).into(ivArticelImage)
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(article)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}
