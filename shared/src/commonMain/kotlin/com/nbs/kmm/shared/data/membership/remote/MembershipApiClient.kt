package com.nbs.kmm.shared.data.membership.remote

import com.nbs.kmm.shared.data.membership.model.request.LoginRequest
import com.nbs.kmm.shared.data.membership.model.request.RegisterRequest
import com.nbs.kmm.shared.data.membership.model.response.LoginDataResponse
import com.nbs.kmm.shared.data.membership.model.response.RegisterResponse

interface MembershipApiClient {
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse
    suspend fun login(loginRequest: LoginRequest): LoginDataResponse
}