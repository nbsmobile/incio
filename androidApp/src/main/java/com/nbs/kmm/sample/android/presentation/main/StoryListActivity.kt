package com.nbs.kmm.sample.android.presentation.main

import android.content.Context
import android.content.Intent
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityStoryListBinding
import com.nbs.kmm.sample.android.presentation.membership.login.LoginActivity
import com.nbs.kmm.sample.android.presentation.post.PostStoryActivity
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.showAlertDialog
import com.nbs.kmm.sample.android.utils.util.showErrorAlert
import com.nbs.kmm.sample.android.viewmodel.StoryViewModel
import com.nbs.kmm.shared.domain.account.AccountManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryListActivity : SampleBaseActivity<ActivityStoryListBinding>() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, StoryListActivity::class.java))
        }
    }

    private val storyAdapter by lazy { StoryAdapter {} }

    private val storyViewModel: StoryViewModel by viewModel()

    private val accountManager: AccountManager by inject()

    override fun getViewBinding() = ActivityStoryListBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {
        with(binding) {
            rvStory.adapter = storyAdapter
        }
    }

    override fun initAction() {
        with(binding) {
            layoutToolbar.btnAddStory.setOnClickListener {
                PostStoryActivity.start(this@StoryListActivity)
            }

            layoutToolbar.btnLogout.setOnClickListener {
                showAlertDialog(
                    context = this@StoryListActivity,
                    title = "Logout",
                    message = "Apakah kamu yakin ingin logout?",
                    positive = "Logout",
                    positiveListener = {
                        accountManager.logout()
                        LoginActivity.start(this@StoryListActivity)
                        finishAffinity()
                    },
                    negative = "Tidak",
                    negativeListener = null
                )
            }
        }
    }

    override fun initProses() {
        storyViewModel.fetchStories(1, 20, false)
    }

    override fun initObservers() {
        storyViewModel.storyResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    storyAdapter.setItem(it.data.toMutableList())
                }

                is Resource.Failure -> {
                    hideLoading()
                    showErrorAlert(it.throwable, it.message.toString())
                }
                else -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        storyViewModel.fetchStories(1, 20, false)
    }
}