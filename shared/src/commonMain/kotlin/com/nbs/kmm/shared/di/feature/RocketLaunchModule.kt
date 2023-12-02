package com.nbs.kmm.shared.di.feature

import com.nbs.kmm.shared.data.rocketlaunch.RocketLaunchDataStore
import com.nbs.kmm.shared.data.rocketlaunch.RocketLaunchRepository
import com.nbs.kmm.shared.data.rocketlaunch.remote.RocketLaunchApi
import com.nbs.kmm.shared.data.rocketlaunch.remote.RocketLaunchApiClient
import com.nbs.kmm.shared.di.NETWORK_CLIENT
import com.nbs.kmm.shared.di.NETWORK_CLIENT_SPACEX
import com.nbs.kmm.shared.domain.rocketlaunch.RocketLaunchInteractor
import com.nbs.kmm.shared.domain.rocketlaunch.RocketLaunchUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val rocketLaunchModule = module {
    single<RocketLaunchApiClient> { RocketLaunchApi(get(named(NETWORK_CLIENT_SPACEX))) }

    single<RocketLaunchRepository> { RocketLaunchDataStore(get(), get()) }

    single<RocketLaunchUseCase> { RocketLaunchInteractor(get()) }
}