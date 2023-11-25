package com.nbs.kmm.shared.data.membership.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerialName("message")
    val message: String?,
    @SerialName("error")
    val error: Boolean?
)