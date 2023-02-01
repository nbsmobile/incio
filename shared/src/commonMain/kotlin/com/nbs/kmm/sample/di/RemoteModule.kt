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
const val NETWORK_CLIENT = "shared_network_client"

@ExperimentalSerializationApi
val remoteModule = module {
    single { setupJson() }

    single { setupKermit() }

    single(named(BASE_URL)) { NbsKmmSharedPublicConfig.BASE_URL }

    single(named(NETWORK_CLIENT)) {
        setupHttpClient(
            json = get(),
            baseUrl = get(named(BASE_URL)),
            kermitLogger = get(),
            isDebugMode = getPlatform().isDebugMode(),
            accountManager = get(),
            httpClientProvider = getPlatform().getHttpClientEngine()
        )
    }
}