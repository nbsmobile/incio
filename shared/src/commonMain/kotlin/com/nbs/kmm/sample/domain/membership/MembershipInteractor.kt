package com.nbs.kmm.sample.domain.membership

import com.nbs.kmm.sample.data.membership.MembershipRepository
import com.nbs.kmm.sample.data.membership.model.request.LoginRequest
import com.nbs.kmm.sample.domain.account.AccountManager
import com.nbs.kmm.sample.domain.account.model.UserData
import com.nbs.kmm.sample.domain.base.execute
import com.nbs.kmm.sample.domain.membership.mapper.toDomain
import com.nbs.kmm.sample.domain.membership.mapper.toRequest
import com.nbs.kmm.sample.domain.membership.model.Login
import com.nbs.kmm.sample.domain.membership.model.RegisterParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class MembershipInteractor(
    private val membershipRepository: MembershipRepository,
    private val accountManager: AccountManager
) : MembershipUseCase {
    override suspend fun register(registerParam: RegisterParam): Flow<Boolean> {
        return execute(context = Dispatchers.Main) {
            membershipRepository.register(registerParam.toRequest())
            true
        }
    }

    override suspend fun login(email: String, password: String): Flow<Login> {
        return execute(context = Dispatchers.Main) {
            val response = membershipRepository.login(LoginRequest(email = email, password = password)).toDomain()
            handleToken(response)
            response
        }
    }

    private fun handleToken(login: Login) {
        if (login.token.isNotEmpty()) accountManager.storeToken(authToken = login.token)

        accountManager.storeUserData(UserData(userId = login.userId, name = login.name))
    }
}