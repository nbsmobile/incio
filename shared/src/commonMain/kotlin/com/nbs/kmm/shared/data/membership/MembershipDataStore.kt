package com.nbs.kmm.shared.data.membership

import com.nbs.kmm.shared.data.membership.model.request.LoginRequest
import com.nbs.kmm.shared.data.membership.model.request.RegisterRequest
import com.nbs.kmm.shared.data.membership.model.response.LoginDataResponse
import com.nbs.kmm.shared.data.membership.model.response.RegisterResponse
import com.nbs.kmm.shared.data.membership.remote.MembershipApiClient

class MembershipDataStore(private val membershipApiClient: MembershipApiClient): MembershipRepository {
    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse {
        return membershipApiClient.register(registerRequest)
    }

    override suspend fun login(loginRequest: LoginRequest): LoginDataResponse {
        return membershipApiClient.login(loginRequest)
    }
}