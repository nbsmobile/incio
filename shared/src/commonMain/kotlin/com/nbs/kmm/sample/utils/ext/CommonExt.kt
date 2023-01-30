package com.nbs.kmm.sample.utils.ext

private const val BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0

fun Double?.orZero() = this ?: 0

fun Int.toBase64(): Char = BASE64_ALPHABET[this]