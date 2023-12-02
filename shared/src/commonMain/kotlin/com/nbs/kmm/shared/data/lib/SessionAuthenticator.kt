package com.nbs.kmm.shared.data.lib

import com.nbs.kmm.shared.data.base.HttpClientInterceptor
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.coroutines.sync.Mutex

class SessionAuthenticator : HttpClientInterceptor() {

    private val mutex = Mutex()

    override val key: AttributeKey<HttpClientInterceptor> = AttributeKey("SessionAuthenticator")

    override fun onRequestPipeline(client: HttpClient, requestBuilder: HttpRequestBuilder) {
    }

    override suspend fun onResponsePipeline(client: HttpClient, responseCall: HttpClientCall) {
        if (responseCall.response.status.value == 401) {
            //TODO AUTH
        }
    }

    override fun prepare(block: Config.() -> Unit): HttpClientInterceptor {
        return SessionAuthenticator()
    }
}