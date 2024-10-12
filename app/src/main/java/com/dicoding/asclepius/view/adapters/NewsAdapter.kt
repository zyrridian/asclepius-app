package com.dicoding.asclepius.view.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.asclepius.data.local.entity.NewsEntity
import com.dicoding.asclepius.databinding.ItemDataNewsBinding
import com.dicoding.asclepius.databinding.ItemShimmerNewsBinding

class NewsAdapter(
    private var isLoading: Boolean = true
) : ListAdapter<NewsEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) VIEW_TYPE_SHIMMER else VIEW_TYPE_DATA
        // todo: shimmer OR normal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val binding = ItemShimmerNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ShimmerViewHolder(binding)
        } else {
            val binding = ItemDataNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            MyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder && !isLoading) {
            val news = getItem(position)
            holder.bind(news)
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) 5 else super.getItemCount()
    }

    class ShimmerViewHolder(binding: ItemShimmerNewsBinding) : RecyclerView.ViewHolder(binding.root)

    class MyViewHolder(val binding: ItemDataNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsEntity) {
            binding.titleTextView.text = news.title
            binding.descriptionTextView.text = news.description
            Glide.with(binding.root.context)
                .load(news.urlToImage)
                .transform(RoundedCorners(16))
                .into(binding.imageView)
            itemView.setOnClickListener {
                CustomTabsIntent.Builder().build().launchUrl(itemView.context, Uri.parse(news.url))
            }
        }
    }

    fun setLoadingState(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_SHIMMER = 0
        private const val VIEW_TYPE_DATA = 1

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}