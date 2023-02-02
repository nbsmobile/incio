package com.nbs.kmm.sample.android.di

import com.nbs.kmm.sample.android.presentation.viewmodel.MembershipViewModel
import com.nbs.kmm.sample.android.sample.rocketlaunch.RocketLaunchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { RocketLaunchViewModel(get(), get()) }

    viewModel { MembershipViewModel(get(), get()) }
}