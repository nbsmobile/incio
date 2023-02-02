package com.nbs.kmm.sample.data.lib

import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import com.nbs.kmm.sample.data.base.ApiException
import com.nbs.kmm.sample.domain.account.AccountManager
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

//for logging
fun setupKermit(): Logger {
    return Logger(config = LoggerConfig.default)
}

//for json serialization
@ExperimentalSerializationApi
fun setupJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
    explicitNulls = false
}

@ExperimentalSerializationApi
fun setupHttpClient(
    json: Json,
    baseUrl: String,
    kermitLogger: Logger,
    isDebugMode: Boolean = true,
    accountManager: AccountManager,
    httpClientProvider: HttpClient
): HttpClient {

    return httpClientProvider.config {

        ContentEncoding()

        expectSuccess = true

        install(ContentNegotiation) {
            json(setupJson())
        }

        install(HeaderInterceptor(accountManager))

        install(SessionAuthenticator())

        defaultRequest {
            host = baseUrl

            url {
                this.user
                protocol = URLProtocol.HTTPS

                appendPathSegments("v1/")
            }
        }

        install(HttpTimeout) {
            this.requestTimeoutMillis = 60000
            this.connectTimeoutMillis = 60000
            this.socketTimeoutMillis = 60000
        }

        HttpResponseValidator {
            handleResponseExceptionWithRequest { cause, _ ->
                when (cause) {
                    is ServerResponseException -> {
                        val serverResponseResponse = cause.response
                        val serverResponseExceptionText = serverResponseResponse.bodyAsText().trimIndent()
                        val apiException = json.decodeFromString(
                            ApiException.serializer(),
                            serverResponseExceptionText
                        )
                        throw apiException
                    }
                    is ClientRequestException -> {
                        val exceptionResponse = cause.response
                        val exceptionResponseText = exceptionResponse.bodyAsText().trimIndent()
                        val apiException =
                            json.decodeFromString(ApiException.serializer(), exceptionResponseText)
                        throw apiException
                    }
                    else -> {
                        throw cause
                    }
                }
            }
        }

        if (isDebugMode) {
            install(Logging) {
                logger = CustomLogger(kermitLogger)
                level = LogLevel.ALL
            }
        }
    }
}

class CustomLogger(private val log: Logger) : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        return log.withTag("TAG").d(message)
    }
}