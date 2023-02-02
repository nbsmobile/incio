package com.nbs.kmm.sample.data.lib

import com.nbs.kmm.sample.data.base.HttpClientInterceptor
import com.nbs.kmm.sample.domain.account.AccountManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class HeaderInterceptor(
    private val accountManager: AccountManager
) : HttpClientInterceptor() {

    override val key: AttributeKey<HttpClientInterceptor> = AttributeKey("HeaderInterceptor")

    override fun onRequestPipeline(client: HttpClient, requestBuilder: HttpRequestBuilder) {
        val path = requestBuilder.url.encodedPath

        if (!path.contains("login")
            || !path.contains("register")
            || !path.contains("stories/guest")
        ) {
            requestBuilder.headers.append("Authorization", "Bearer ${accountManager.getToken()}")
        }
    }

    override suspend fun onResponsePipeline(client: HttpClient, responseCall: HttpClientCall) {
    }

    override fun prepare(block: Config.() -> Unit): HttpClientInterceptor {
        return HeaderInterceptor(accountManager)
    }
}