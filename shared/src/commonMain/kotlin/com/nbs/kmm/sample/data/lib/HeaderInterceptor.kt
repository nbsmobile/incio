package com.nbs.kmm.sample.data.lib

import com.nbs.kmm.sample.data.base.HttpClientInterceptor
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class HeaderInterceptor : HttpClientInterceptor() {

    override val key: AttributeKey<HttpClientInterceptor> = AttributeKey("HeaderInterceptor")

    override fun onRequestPipeline(client: HttpClient, requestBuilder: HttpRequestBuilder) {
        val path = requestBuilder.url.encodedPath
        requestBuilder.headers.append(
            "Authorization", when {
                path.contains("/anon/") -> "Basic key"
                else -> "Bearer key"
            }
        )
    }

    override suspend fun onResponsePipeline(client: HttpClient, responseCall: HttpClientCall) {
    }

    override fun prepare(block: Config.() -> Unit): HttpClientInterceptor {
        return HeaderInterceptor()
    }
}