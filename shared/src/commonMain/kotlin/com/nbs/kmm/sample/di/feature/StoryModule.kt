package com.nbs.kmm.sample.di.feature

import com.nbs.kmm.sample.data.story.StoryDataStore
import com.nbs.kmm.sample.data.story.StoryRepository
import com.nbs.kmm.sample.data.story.remote.StoryApi
import com.nbs.kmm.sample.data.story.remote.StoryApiClient
import com.nbs.kmm.sample.di.NETWORK_CLIENT
import com.nbs.kmm.sample.di.NETWORK_CLIENT_MULTIPART
import com.nbs.kmm.sample.domain.story.StoryInteractor
import com.nbs.kmm.sample.domain.story.StoryUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val storyModule = module {
    single<StoryApiClient> {
        StoryApi(
            get(named(NETWORK_CLIENT)),
            get(named(NETWORK_CLIENT_MULTIPART))
        )
    }

    single<StoryRepository> { StoryDataStore(get()) }

    single<StoryUseCase> { StoryInteractor(get()) }
}