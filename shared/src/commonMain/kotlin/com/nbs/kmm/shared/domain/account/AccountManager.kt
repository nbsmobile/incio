package com.nbs.kmm.shared.domain.account

import com.nbs.kmm.shared.domain.account.model.UserData

interface AccountManager {

    fun storeToken(authToken: String)

    fun getToken(): String?

    fun storeUserData(userData: UserData)

    fun getUserData(): UserData

    fun clearAllData()

    fun isLoggedIn(): Boolean

    fun logout()

}