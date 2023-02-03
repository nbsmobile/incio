package com.nbs.kmm.sample.data.membership.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerialName("message")
    val message: String?,
    @SerialName("error")
    val error: Boolean?
)