package com.nbs.kmm.sample

import com.nbs.kmm.sample.cache.IOSDatabaseDriverFactory
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
}

actual fun getPlatform(): Platform = IOSPlatform()