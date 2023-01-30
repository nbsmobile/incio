package com.nbs.kmm.sample.utils

import com.nbs.kmm.sample.data.lib.CustomLogger
import com.nbs.kmm.sample.data.lib.setupKermit
import com.nbs.kmm.sample.getPlatform
import com.nbs.kmm.sample.utils.ext.toBase64
import io.ktor.utils.io.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun emptyString() = ""

private const val BASE64_MASK: Byte = 0x3f
private const val BASE64_PAD = '='

fun String.encodeBase64(): String = buildPacket {
    writeText(this@encodeBase64)
}.encodeBase64()

private fun ByteReadPacket.encodeBase64(): String = buildString {
    val data = ByteArray(3)
    while (remaining > 0) {
        val read = readAvailable(data)
        data.clearFrom(read)

        val padSize = (data.size - read) * 8 / 6
        val chunk = ((data[0].toInt() and 0xFF) shl 16) or
                ((data[1].toInt() and 0xFF) shl 8) or
                (data[2].toInt() and 0xFF)

        for (index in data.size downTo padSize) {
            val char = (chunk shr (6 * index)) and BASE64_MASK.toInt()
            append(char.toBase64())
        }

        repeat(padSize) { append(BASE64_PAD) }
    }
}

private fun ByteArray.clearFrom(from: Int) {
    (from until size).forEach { this[it] = 0 }
}


fun <T> Flow<Any>.butReturn(data: T): Flow<T> = flowOf(data)

fun logging(message: () -> String) {
    if (getPlatform().isDebugMode()) {
        CustomLogger(setupKermit()).log(message.invoke())
    }
}

inline fun silence(body: () -> Unit) {
    try {
        body()
    } catch (e: Exception) {
        //Nothing
    }
}