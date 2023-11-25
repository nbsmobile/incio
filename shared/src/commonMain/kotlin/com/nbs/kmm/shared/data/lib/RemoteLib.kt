package com.nbs.kmm.shared.data.lib

import co.touchlab.kermit.Logger
import co.touchlab.kermit.LoggerConfig
import com.nbs.kmm.shared.data.base.ApiException
import com.nbs.kmm.shared.domain.account.AccountManager
import com.nbs.kmm.shared.getPlatform
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
    apiVersion: String,
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
            getPlatform().getRequestHash()
            host = baseUrl

            url {
                this.user
                protocol = URLProtocol.HTTPS

                appendPathSegments(apiVersion)
            }
        }

        install(HttpTimeout) {
            this.requestTimeoutMillis = 600000
            this.connectTimeoutMillis = 600000
            this.socketTimeoutMillis = 600000
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
                        throw apiException.copy(httpCode = cause.response.status.value)
                    }
                    is ClientRequestException -> {
                        val exceptionResponse = cause.response
                        val exceptionResponseText = exceptionResponse.bodyAsText().trimIndent()
                        val apiException =
                            json.decodeFromString(ApiException.serializer(), exceptionResponseText)
                        throw apiException.copy(httpCode = cause.response.status.value)
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