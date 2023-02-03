package com.nbs.kmm.sample.android.presentation

import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityMainBinding
import com.nbs.kmm.sample.android.presentation.membership.login.LoginActivity
import com.nbs.kmm.sample.android.presentation.preference.PreferenceDemoActivity
import com.nbs.kmm.sample.android.presentation.rocketlaunch.RocketLaunchActivity
import com.nbs.kmm.sample.android.utils.enums.MainMenu
import com.nbs.kmm.sample.android.utils.util.gone
import com.nbs.kmm.sample.android.utils.util.visible

class MainActivity : SampleBaseActivity<ActivityMainBinding>() {

    private val mainMenuAdapter by lazy {
        MainMenuAdapter {
            when (it) {
                MainMenu.DICODING_API.menu -> {
                    LoginActivity.start(this)
                }
                MainMenu.ROCKET_LAUNCH.menu -> {
                    RocketLaunchActivity.start(this)
                }
                MainMenu.PREFERENCE.menu -> {
                    PreferenceDemoActivity.start(this)
                }
            }
        }
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {
        with(binding) {
            layoutToolbar.tvTitle.visible()
            layoutToolbar.btnLogout.gone()
            layoutToolbar.btnAddStory.gone()
            layoutToolbar.btnProfile.gone()

            rvMain.adapter = mainMenuAdapter

            mainMenuAdapter.setItem(MainMenu.values().map { it.menu }.toMutableList())
        }
    }

    override fun initAction() {
    }

    override fun initProses() {
    }

    override fun initObservers() {
    }
}