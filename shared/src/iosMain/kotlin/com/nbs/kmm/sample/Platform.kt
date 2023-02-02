package com.nbs.kmm.sample

import com.nbs.kmm.sample.cache.IOSDatabaseDriverFactory
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import platform.UIKit.UIDevice

class IOSPlatform : Platform {

    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override fun getHttpClientEngine(): HttpClient {
        return HttpClient(Darwin) {
            engine {
                configureRequest {
                    setAllowsCellularAccess(true)
                }
            }
        }
    }

    override fun isDebugMode(): Boolean {
        return true
    }

    override fun getDatabaseDriver(): SqlDriver {
        return IOSDatabaseDriverFactory().createDriver()
    }

    @OptIn(ExperimentalSettingsImplementation::class)
    override fun getEncryptedPreference(preferenceName: String): Settings {
        return KeychainSettings(preferenceName)
    }
}

actual fun getPlatform(): Platform = IOSPlatform()