package com.nbs.kmm.sample.data.base

abstract class BaseResponse {
    abstract val isError: Boolean?
    abstract val message: String?
}