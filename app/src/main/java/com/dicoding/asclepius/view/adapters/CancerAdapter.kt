package com.dicoding.asclepius.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.CancerEntity
import com.dicoding.asclepius.databinding.ItemDataCancerBinding
import com.dicoding.asclepius.databinding.ItemShimmerCancerBinding
import com.dicoding.asclepius.helper.ImageConverter
import java.text.NumberFormat

class CancerAdapter(
    private var isLoading: Boolean = true
) : ListAdapter<CancerEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) VIEW_TYPE_SHIMMER else VIEW_TYPE_DATA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val binding = ItemShimmerCancerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ShimmerViewHolder(binding)
        } else {
            val binding = ItemDataCancerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            MyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder && !isLoading) {
            val cancer = getItem(position)
            holder.bind(cancer)
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) 7 else super.getItemCount()
    }

    class ShimmerViewHolder(binding: ItemShimmerCancerBinding) :
        RecyclerView.ViewHolder(binding.root)

    class MyViewHolder(private val binding: ItemDataCancerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cancer: CancerEntity) {
            binding.labelTextView.text = cancer.label
            binding.confidenceTextView.text =
                NumberFormat.getPercentInstance().format(cancer.confidence).trim()
            Glide.with(binding.root.context)
                .load(cancer.image?.let { ImageConverter.byteArrayToBitmap(it) })
                .transform(RoundedCorners(16))
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.imageView)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLoadingState(isLoading: Boolean) {
        this.isLoading = isLoading
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_SHIMMER = 0
        private const val VIEW_TYPE_DATA = 1

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CancerEntity>() {
            override fun areItemsTheSame(oldItem: CancerEntity, newItem: CancerEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: CancerEntity, newItem: CancerEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
