package com.nbs.kmm.sample.android.presentation.main

import android.content.Context
import android.content.Intent
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityMainBinding
import com.nbs.kmm.sample.android.presentation.post.PostStoryActivity

class MainActivity : SampleBaseActivity<ActivityMainBinding>() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val storyAdapter by lazy {
        StoryAdapter {}
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {
        with(binding) {
            rvStory.adapter = storyAdapter
        }
    }

    override fun initAction() {
        with(binding) {
            layoutToolbar.btnAddStory.setOnClickListener {
                PostStoryActivity.start(this@MainActivity)
            }
        }
    }

    override fun initProses() {}

    override fun initObservers() {}
}