package com.nbs.kmm.sample

import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*

interface Platform {
    val name: String
    fun getHttpClientEngine(): HttpClient
    fun isDebugMode(): Boolean
    fun getDatabaseDriver(dbName: String, passphrase: String) : SqlDriver

    fun getEncryptedPreference(preferenceName: String): Settings
}

expect fun getPlatform(): Platform