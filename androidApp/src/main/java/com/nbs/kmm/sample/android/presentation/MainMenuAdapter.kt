package com.nbs.kmm.sample.android.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nbs.kmm.sample.android.base.BaseRecyclerViewAdapter
import com.nbs.kmm.sample.android.databinding.ItemMenuMainBinding

class MainMenuAdapter(
    private val onItemClicked: (String) -> Unit
) : BaseRecyclerViewAdapter<MainMenuAdapter.MainMenuViewHolder, String>() {

    inner class MainMenuViewHolder(private val binding: ItemMenuMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            with(binding) {
                tvMainMenu.text = data
                tvMainMenu.setOnClickListener {
                    onItemClicked.invoke(data)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MainMenuViewHolder, item: String, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder {
        return MainMenuViewHolder(
            ItemMenuMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}