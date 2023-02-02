package com.nbs.kmm.sample.domain.membership

import com.nbs.kmm.sample.domain.membership.model.Login
import com.nbs.kmm.sample.domain.membership.model.RegisterParam
import kotlinx.coroutines.flow.Flow

interface MembershipUseCase {
    suspend fun register(registerParam: RegisterParam): Flow<Boolean>
    suspend fun login(email: String, password: String): Flow<Login>
}