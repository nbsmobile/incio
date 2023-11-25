package com.nbs.kmm.shared.data.base

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

abstract class HttpClientInterceptor :
    HttpClientPlugin<HttpClientInterceptor.Config, HttpClientInterceptor> {

    abstract fun onRequestPipeline(client: HttpClient, requestBuilder: HttpRequestBuilder)

    abstract suspend fun onResponsePipeline(client: HttpClient, responseCall: HttpClientCall)

    abstract class Config

    override fun install(plugin: HttpClientInterceptor, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.Transform) {
            onRequestPipeline(scope, context)
        }

        scope.responsePipeline.intercept(HttpResponsePipeline.Transform) { (_, _) ->
            onResponsePipeline(scope, context)
        }
    }
}