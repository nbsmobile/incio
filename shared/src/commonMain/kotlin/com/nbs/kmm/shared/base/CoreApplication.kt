@file:OptIn(ExperimentalSerializationApi::class)

package com.nbs.kmm.shared.base

import com.nbs.kmm.shared.di.databaseModule
import com.nbs.kmm.shared.di.feature.membershipModule
import com.nbs.kmm.shared.di.feature.rocketLaunchModule
import com.nbs.kmm.shared.di.feature.storyModule
import com.nbs.kmm.shared.di.preferenceModule
import com.nbs.kmm.shared.di.remoteModule
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import kotlin.jvm.JvmStatic

object CoreApplication {

    @JvmStatic
    fun initialize(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
        return startKoin {
            appDeclaration()
            modules(getCoreModules())
        }
    }

    private fun getCoreModules(): List<Module> {
        return listOf(
            remoteModule,
            preferenceModule,
            databaseModule,
            rocketLaunchModule,
            storyModule,
            membershipModule
        )
    }
}