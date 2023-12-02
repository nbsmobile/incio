package com.nbs.kmm.shared.data.membership.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDataResponse(
    @SerialName("error")
    val error: Boolean?,
    @SerialName("message")
    val message: String?,
    @SerialName("loginResult")
    val loginResult: LoginResponse?
)

@Serializable
data class LoginResponse(
    @SerialName("token")
    val token: String?,
    @SerialName("userId")
    val userId: String?,
    @SerialName("name")
    val name: String?
)