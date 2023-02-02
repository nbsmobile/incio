package com.nbs.kmm.sample

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nbs.kmm.sample.cache.AndroidDatabaseDriverFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
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

    override fun getDatabaseDriver(dbName: String, passphrase: String): SqlDriver {
        return AndroidDatabaseDriverFactory(context, dbName, passphrase).createDriver()
    }

    override fun getEncryptedPreference(preferenceName: String): Settings {
        return SharedPreferencesSettings(
            delegate = EncryptedSharedPreferences.create(
                preferenceName,
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        )
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()