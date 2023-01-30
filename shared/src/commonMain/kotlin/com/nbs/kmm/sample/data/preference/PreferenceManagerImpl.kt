package com.nbs.kmm.sample.data.preference

import com.russhwolf.settings.Settings

class PreferenceManagerImpl(private val preference: Settings) : PreferenceManager {

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return preference.getBoolean(key, default)
    }

    override fun saveBoolean(key: String, value: Boolean) {
        preference.putBoolean(key, value)
    }

    override fun getInt(key: String): Int? {
        return preference.getIntOrNull(key)
    }

    override fun saveInt(key: String, value: Int) {
        preference.putInt(key, value)
    }

    override fun getString(key: String): String? {
        return preference.getStringOrNull(key)
    }

    override fun saveString(key: String, value: String) {
        preference.putString(key, value)
    }

    override fun getLong(key: String): Long? {
        return preference.getLongOrNull(key)
    }

    override fun saveLong(key: String, value: Long) {
        preference.putLong(key, value)
    }

    override fun getFloat(key: String): Float? {
        return preference.getFloatOrNull(key)
    }

    override fun saveFloat(key: String, value: Float) {
        preference.putFloat(key, value)
    }

    override fun clear() {
        preference.clear()
    }

}
