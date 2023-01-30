package com.nbs.kmm.sample.data.preference

interface PreferenceManager {
    fun getBoolean(key: String, default: Boolean = false): Boolean
    fun saveBoolean(key: String, value: Boolean)

    fun getInt(key: String): Int?
    fun saveInt(key: String, value: Int)

    fun getString(key: String): String?
    fun saveString(key: String, value: String)

    fun getLong(key: String): Long?
    fun saveLong(key: String, value: Long)

    fun getFloat(key: String): Float?
    fun saveFloat(key: String, value: Float)

    fun clear()
}