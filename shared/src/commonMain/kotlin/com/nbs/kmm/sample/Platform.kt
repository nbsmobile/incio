package com.nbs.kmm.sample

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*

interface Platform {
    val name: String
    fun getHttpClientEngine(): HttpClient
    fun isDebugMode(): Boolean
    fun getDatabaseDriver() : SqlDriver
}

expect fun getPlatform(): Platform