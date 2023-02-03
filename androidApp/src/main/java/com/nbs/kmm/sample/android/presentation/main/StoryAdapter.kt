package com.nbs.kmm.sample.android.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nbs.kmm.sample.android.base.BaseRecyclerViewAdapter
import com.nbs.kmm.sample.android.databinding.ItemStoryBinding
import com.nbs.kmm.sample.android.utils.util.setImageUrl
import com.nbs.kmm.sample.domain.story.model.Story

class StoryAdapter(
    private val onItemClicked: (Story) -> Unit
) : BaseRecyclerViewAdapter<StoryAdapter.NewsViewHolder, Story>() {

    inner class NewsViewHolder(private val binding: ItemStoryBinding) : ViewHolder(binding.root) {
        fun onBind(data: Story) {
            with(binding) {
                tvUsername.text = data.name
                tvDescription.text = data.description
                ivStory.setImageUrl(data.photoUrl,true)
                root.setOnClickListener { onItemClicked.invoke(data) }
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, item: Story, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}