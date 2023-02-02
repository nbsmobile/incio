package com.nbs.kmm.sample.android.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder, T> :
    RecyclerView.Adapter<VH>() {

    protected var items: MutableList<T>

    abstract fun onBindViewHolder(holder: VH, item: T, position: Int)

    init {
        items = mutableListOf()
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, this.items[position], position)
    }

    fun getItem(position: Int): T? = items[position]

    private fun addItem(item: MutableList<T>) {
        clear()
        this.items.addAll(item)
        this.notifyDataSetChanged()
    }

    private fun clear() {
        this.items.clear()
    }

    fun setItem(item: MutableList<T>) {
        clear()
        addItem(item)
    }
}