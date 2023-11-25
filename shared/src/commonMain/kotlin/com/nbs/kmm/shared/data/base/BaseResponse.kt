package com.nbs.kmm.shared.data.base

abstract class BaseResponse {
    abstract val isError: Boolean?
    abstract val message: String?
}