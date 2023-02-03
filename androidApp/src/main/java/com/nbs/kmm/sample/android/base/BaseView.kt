package com.nbs.kmm.sample.android.base

interface BaseView {
    fun hideLoading()

    fun setupToolbar(toolbar: androidx.appcompat.widget.Toolbar, isChild: Boolean)

    fun setupToolbar(
        toolbar: androidx.appcompat.widget.Toolbar,
        title: String,
        isChild: Boolean
    )

    fun showLoading()
}
