package com.nbs.kmm.sample.data.base

import com.nbs.kmm.sample.domain.base.ApiError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiException(
    @SerialName("code")
    val errorCode: String,
    @SerialName("message")
    val errorMessage: String,
    @SerialName("success")
    val status: Boolean
): Exception() {
    fun map(code: String?, message: String, status: Boolean): ApiError {
        return ApiError(errorCode = code ?: "", errorMessage = message, status = status)
    }
}