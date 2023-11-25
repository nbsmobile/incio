package com.nbs.kmm.shared.domain.account

import com.nbs.kmm.shared.domain.account.model.UserData
import com.nbs.kmm.shared.utils.emptyString
import com.nbs.kmm.shared.utils.enum.PreferenceKey
import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json

class AccountManagerImpl(
    private val preference: Settings,
    private val json: Json
): AccountManager {
    override fun storeToken(authToken: String) {
        preference.putString(PreferenceKey.AUTH_TOKEN, authToken)
    }

    override fun getToken(): String? {
        return preference.getStringOrNull(PreferenceKey.AUTH_TOKEN)
    }

    override fun storeUserData(userData: UserData) {
        val json = json.encodeToString(UserData.serializer(), userData)
        preference.putString(PreferenceKey.USER_DATA, json)
    }

    override fun getUserData(): UserData {
        val userData = preference.getString(PreferenceKey.USER_DATA, emptyString())

        return if (userData.isNotEmpty()) {
            try {
                json.decodeFromString(UserData.serializer(), userData)
            } catch (e: Exception) {
                UserData()
            }
        } else {
            UserData()
        }
    }

    override fun clearAllData() {
        preference.remove(PreferenceKey.AUTH_TOKEN)
        preference.remove(PreferenceKey.USER_DATA)

    }

    override fun isLoggedIn(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    override fun logout() {
        clearAllData()
    }
}