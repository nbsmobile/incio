package com.nbs.kmm.sample

import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*

interface Platform {
    val name: String
    fun getHttpClientEngine(forMultipartData: Boolean = false): HttpClient
    fun isDebugMode(): Boolean
    fun getDatabaseDriver(dbName: String, passphrase: String) : SqlDriver
    fun getEncryptedPreference(preferenceName: String): Settings
    fun getRequestHash(): String
}

expect fun getPlatform(): Platform