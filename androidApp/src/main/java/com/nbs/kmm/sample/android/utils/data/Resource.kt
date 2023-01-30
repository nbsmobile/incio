package com.nbs.kmm.sample.android.utils.data

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Default<T> : Resource<T>()
    class Empty<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure<T>(val throwable: Throwable?, val message: String?) : Resource<T>()

    companion object {
        fun <T> loading(): Resource<T> = Loading()
        fun <T> default(): Resource<T> = Default()
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> empty(): Resource<T> = Empty()
        fun <T> fail(throwable: Throwable, message: String?): Resource<T> = Failure(throwable, message)
        fun <T> fail(message: String?): Resource<T> = Failure(null, message)
    }
}