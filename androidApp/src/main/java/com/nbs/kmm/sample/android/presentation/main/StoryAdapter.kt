package com.nbs.kmm.sample.android.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nbs.kmm.sample.android.base.BaseRecyclerViewAdapter
import com.nbs.kmm.sample.android.databinding.ItemStoryBinding

class StoryAdapter(
    private val onItemClicked: (String) -> Unit
) : BaseRecyclerViewAdapter<StoryAdapter.NewsViewHolder, String>() {

    inner class NewsViewHolder(private val binding: ItemStoryBinding) : ViewHolder(binding.root) {
        fun onBind(data: String) {
            with(binding) {
//                tvTitle.text = news.label
//                tvDescription.text = news.description
//                ivNews.setImageDrawable(
//                    ContextCompat.getDrawable(binding.root.context, news.image)
//                )
                root.setOnClickListener { onItemClicked.invoke(data) }
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, item: String, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}