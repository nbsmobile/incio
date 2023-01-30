package com.nbs.kmm.sample.domain.base

data class ApiError(
    val errorCode: String,
    val errorMessage: String,
    val status: Boolean
): Exception()