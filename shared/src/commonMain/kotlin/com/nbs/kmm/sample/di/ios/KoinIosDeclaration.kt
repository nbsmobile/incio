package com.nbs.kmm.sample.di.ios

import com.nbs.kmm.sample.base.CoreApplication
import com.nbs.kmm.sample.data.preference.PreferenceManager
import com.nbs.kmm.sample.domain.rocketlaunch.RocketLaunchUseCase
import org.koin.core.Koin
import org.koin.core.KoinApplication

// Koin utilities for iOS injection

fun KoinApplication.Companion.start(): KoinApplication = CoreApplication.initialize {}

val Koin.rocketLaunchUseCase: RocketLaunchUseCase
    get() = get()

val Koin.preferenceManager: PreferenceManager
    get() = get()