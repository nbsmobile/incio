package com.nbs.kmm.sample.android.di

import com.nbs.kmm.sample.android.rocketlaunch.RocketLaunchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel {
        RocketLaunchViewModel(get(), get())
    }
}