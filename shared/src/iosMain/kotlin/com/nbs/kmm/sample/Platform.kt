package com.nbs.kmm.sample

import com.nbs.kmm.sample.cache.IOSDatabaseDriverFactory
import com.nbs.kmm.sample.utils.logging
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import kotlinx.cinterop.refTo
import platform.CoreCrypto.CCHmac
import platform.CoreCrypto.CCHmacAlgorithm
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.CoreCrypto.kCCHmacAlgSHA256
import platform.Foundation.*
import platform.UIKit.UIDevice

class IOSPlatform : Platform {

    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override fun getHttpClientEngine(forMultipartData: Boolean): HttpClient {
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

    override fun getDatabaseDriver(dbName: String, passphrase: String): SqlDriver {
        return IOSDatabaseDriverFactory(dbName, passphrase).createDriver()
    }

    @OptIn(ExperimentalSettingsImplementation::class)
    override fun getEncryptedPreference(preferenceName: String): Settings {
        return KeychainSettings(preferenceName)
    }

    override fun getRequestHash(): String {
        val key = "NBS KMM Sample"
        val timestamp = NSDate().timeIntervalSince1970.toLong().toString()
        val hash = (timestamp as NSString).sha256Hmac(key = key)
        logging { "HASH IOS $hash" }
        return hash
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun NSString.sha256Hmac(algorithm: CCHmacAlgorithm = kCCHmacAlgSHA256, key: String): String {
    val string = this.cStringUsingEncoding(encoding = NSUTF8StringEncoding)
    val stringLength = this.lengthOfBytesUsingEncoding(NSUTF8StringEncoding)
    val digestLength = CC_SHA256_DIGEST_LENGTH
    var result = UByteArray(size = digestLength)
    val keyString = (key as NSString).cStringUsingEncoding(encoding = NSUTF8StringEncoding)
    val keyLength = key.lengthOfBytesUsingEncoding(NSUTF8StringEncoding)

    CCHmac(algorithm, keyString, keyLength, string, stringLength, result.refTo(0))

    return stringFromResult(result, digestLength)
}

@OptIn(ExperimentalUnsignedTypes::class)
fun stringFromResult(result: UByteArray, length: Int): String {
    val hash = StringBuilder()
    for (index in 0 until length) {
        hash.append(NSString.stringWithFormat("%02x", result[index]))
    }
    return hash.toString()
}

actual fun getPlatform(): Platform = IOSPlatform()