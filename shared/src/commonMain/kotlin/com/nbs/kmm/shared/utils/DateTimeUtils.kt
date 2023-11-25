package com.nbs.kmm.shared.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun String.toReadableFullDate(): String {
    try {
        val dateTime = this.toInstant().toLocalDateTime(TimeZone.UTC)
        return "${dateTime.dayOfWeek.ordinal.mapToIndonesianDay()}, ${dateTime.dayOfMonth.mapToReadableDate()} ${dateTime.monthNumber.mapToIndonesianMonth()} ${dateTime.year}"
    } catch (e: Exception) {
        throw Exception("Cannot Parse $this to Date format")
    }
}

fun String.toReadableDate(): String {
    try {
        val dateTime = this.toInstant().toLocalDateTime(TimeZone.UTC)
        return "${dateTime.dayOfMonth.mapToReadableDate()} ${dateTime.monthNumber.mapToIndonesianMonth()} ${dateTime.year}"
    } catch (e: Exception) {
        throw Exception("Cannot Parse $this to Date format")
    }
}

fun String.toSimpleDate(): String {
    try {
        val dateTime = this.toInstant().toLocalDateTime(TimeZone.UTC)
        return "${dateTime.year}-${dateTime.monthNumber.mapToReadableMonth()}-${dateTime.dayOfMonth.mapToReadableDate()}"
    } catch (e: Exception) {
        throw Exception("Cannot Parse $this to Date format")
    }
}

private fun Int.mapToIndonesianMonth(): String {
    val readableMonth = when (this) {
        1 -> "Januari"
        2 -> "Februari"
        3 -> "Maret"
        4 -> "April"
        5 -> "Mei"
        6 -> "Juni"
        7 -> "Juli"
        8 -> "Agustus"
        9 -> "September"
        10 -> "Oktober"
        11 -> "November"
        12 -> "Desember"
        else -> ""
    }
    return readableMonth
}

private fun Int.mapToIndonesianDay(): String {
    val readableDay = when (this) {
        0 -> "Senin"
        1 -> "Selasa"
        2 -> "Rabu"
        3 -> "Kamis"
        4 -> "Jumat"
        5 -> "Sabtu"
        6 -> "Minggu"
        else -> ""
    }
    return readableDay
}

private fun Int.mapToReadableDate(): String {
    return if (this >= 10) this.toString() else "0$this"
}

private fun Int.mapToReadableMonth(): String {
    return if (this >= 10) this.toString() else "0$this"
}

fun Long.getYearFromEpoch(): Int {
    val instant = Instant.fromEpochSeconds(this)
    return instant.toLocalDateTime(TimeZone.UTC).year
}