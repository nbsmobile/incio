package com.nbs.kmm.sample.android.persentation.post

import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityPostStoryBinding

class PostStoryActivity : SampleBaseActivity() {
    private lateinit var binding: ActivityPostStoryBinding
    override fun initBinding() {
        binding = ActivityPostStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initIntent() {}

    override fun initUI() {}

    override fun initAction() {}

    override fun initProses() {}

    override fun initObservers() {}
}