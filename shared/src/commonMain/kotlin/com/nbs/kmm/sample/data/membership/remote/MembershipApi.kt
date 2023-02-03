package com.nbs.kmm.sample.data.membership.remote

import com.nbs.kmm.sample.data.membership.model.request.LoginRequest
import com.nbs.kmm.sample.data.membership.model.request.RegisterRequest
import com.nbs.kmm.sample.data.membership.model.response.LoginDataResponse
import com.nbs.kmm.sample.data.membership.model.response.RegisterResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MembershipApi(private val httpClient: HttpClient) : MembershipApiClient {
    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return httpClient.post("register") {
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }.body()
    }

    override suspend fun login(loginRequest: LoginRequest): LoginDataResponse {
        return httpClient.post("login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }
}