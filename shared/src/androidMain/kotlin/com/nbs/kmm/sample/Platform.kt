package com.nbs.kmm.sample

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nbs.kmm.sample.cache.AndroidDatabaseDriverFactory
import com.nbs.kmm.sample.utils.logging
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.charset.Charset
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

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

    override fun getRequestHash(): String {
        val key: String = "NBS KMM Sample"
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val algorithm: String = "HmacSHA256"
        val charset: Charset = Charset.forName("UTF-8")

        val sha256Hmac: Mac = Mac.getInstance(algorithm)
        val secretKeySpec = SecretKeySpec(key.toByteArray(charset), algorithm)
        sha256Hmac.init(secretKeySpec)
        val hash: String = bytesToHex(sha256Hmac.doFinal(timestamp.toByteArray(charset))).orEmpty()
        logging { "HASH ANDROID $hash" }
        return hash
    }

    private fun bytesToHex(bytes: ByteArray): String? {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()