package com.nbs.kmm.shared.domain.membership

import com.nbs.kmm.shared.data.membership.MembershipRepository
import com.nbs.kmm.shared.data.membership.model.request.LoginRequest
import com.nbs.kmm.shared.domain.account.AccountManager
import com.nbs.kmm.shared.domain.account.model.UserData
import com.nbs.kmm.shared.domain.base.execute
import com.nbs.kmm.shared.domain.membership.mapper.toDomain
import com.nbs.kmm.shared.domain.membership.mapper.toRequest
import com.nbs.kmm.shared.domain.membership.model.Login
import com.nbs.kmm.shared.domain.membership.model.RegisterParam
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
            val response =
                membershipRepository.login(LoginRequest(email = email, password = password))
                    .toDomain()
            handleToken(response)
            response
        }
    }

    private fun handleToken(login: Login) {
        if (login.token.isNotEmpty()) accountManager.storeToken(authToken = login.token)

        accountManager.storeUserData(UserData(userId = login.userId, name = login.name))
    }
}