package com.nbs.kmm.sample

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nbs.kmm.sample.cache.AndroidDatabaseDriverFactory
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AndroidPlatform : Platform, KoinComponent {

    private val context: Context by inject()

    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    override fun getHttpClientEngine(): HttpClient {
        return HttpClient(OkHttp){
            engine {
                config {
                    followRedirects(true)
                }
                if (isDebugMode()){
                    addInterceptor(ChuckerInterceptor.Builder(context).build())
                }
            }
        }
    }

    override fun isDebugMode(): Boolean {
        return BuildConfig.DEBUG
    }

    override fun getDatabaseDriver(): SqlDriver {
        return AndroidDatabaseDriverFactory(context).createDriver()
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()