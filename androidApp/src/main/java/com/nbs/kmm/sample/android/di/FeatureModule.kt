package com.nbs.kmm.sample.android.di

import com.nbs.kmm.sample.android.viewmodel.MembershipViewModel
import com.nbs.kmm.sample.android.presentation.rocketlaunch.RocketLaunchViewModel
import com.nbs.kmm.sample.android.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { RocketLaunchViewModel(get(), get()) }

    viewModel { MembershipViewModel(get(), get()) }

    viewModel { StoryViewModel(get(), get()) }
}