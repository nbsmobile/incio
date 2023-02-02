package com.nbs.kmm.sample.data.membership.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
)