package com.nbs.kmm.sample.di.ios

import com.nbs.kmm.sample.base.CoreApplication
import com.nbs.kmm.sample.data.preference.PreferenceManager
import com.nbs.kmm.sample.domain.account.AccountManager
import com.nbs.kmm.sample.domain.membership.MembershipUseCase
import com.nbs.kmm.sample.domain.rocketlaunch.RocketLaunchUseCase
import com.nbs.kmm.sample.domain.story.StoryUseCase
import org.koin.core.Koin
import org.koin.core.KoinApplication

// Koin utilities for iOS injection

fun KoinApplication.Companion.start(): KoinApplication = CoreApplication.initialize {}

val Koin.rocketLaunchUseCase: RocketLaunchUseCase
    get() = get()

val Koin.accountManager: AccountManager
    get() = get()

val Koin.preferenceManager: PreferenceManager
    get() = get()

val Koin.storyUseCase: StoryUseCase
    get() = get()

val Koin.membershipUseCase: MembershipUseCase
    get() = get()