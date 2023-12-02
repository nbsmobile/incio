package com.nbs.kmm.shared.domain.base

data class ApiError(
    val httpCode: Int,
    val isError: Boolean,
    val errorMessage: String,
    val errorCode: String,
): Exception()