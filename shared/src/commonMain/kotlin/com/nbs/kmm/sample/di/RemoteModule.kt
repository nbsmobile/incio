package com.nbs.kmm.sample.di

import com.nbs.kmm.sample.NbsKmmSharedPublicConfig
import com.nbs.kmm.sample.data.lib.setupHttpClient
import com.nbs.kmm.sample.data.lib.setupJson
import com.nbs.kmm.sample.data.lib.setupKermit
import com.nbs.kmm.sample.getPlatform
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_URL = "base_url"
const val BASE_URL_SPACEX = "base_url_spacex"
const val NETWORK_CLIENT = "shared_network_client"
const val NETWORK_CLIENT_SPACEX = "shared_network_client_spacex"

@ExperimentalSerializationApi
val remoteModule = module {
    single { setupJson() }

    single { setupKermit() }

    single(named(BASE_URL)) { NbsKmmSharedPublicConfig.BASE_URL }

    single(named(BASE_URL_SPACEX)) { NbsKmmSharedPublicConfig.BASE_URL_SPACEX }

    single(named(NETWORK_CLIENT)) {
        setupHttpClient(
            json = get(),
            baseUrl = get(named(BASE_URL)),
            apiVersion = "v1/",
            kermitLogger = get(),
            isDebugMode = getPlatform().isDebugMode(),
            accountManager = get(),
            httpClientProvider = getPlatform().getHttpClientEngine()
        )
    }

    single(named(NETWORK_CLIENT_SPACEX)) {
        setupHttpClient(
            json = get(),
            baseUrl = get(named(BASE_URL_SPACEX)),
            apiVersion = "v4/",
            kermitLogger = get(),
            isDebugMode = getPlatform().isDebugMode(),
            accountManager = get(),
            httpClientProvider = getPlatform().getHttpClientEngine()
        )
    }
}