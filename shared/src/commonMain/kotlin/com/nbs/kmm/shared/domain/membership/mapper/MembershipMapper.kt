package com.nbs.kmm.shared.domain.membership.mapper

import com.nbs.kmm.shared.data.membership.model.request.RegisterRequest
import com.nbs.kmm.shared.data.membership.model.response.LoginDataResponse
import com.nbs.kmm.shared.domain.membership.model.Login
import com.nbs.kmm.shared.domain.membership.model.RegisterParam

fun LoginDataResponse.toDomain(): Login {
    return Login(
        name = loginResult?.name.orEmpty(),
        userId = loginResult?.userId.orEmpty(),
        token = loginResult?.token.orEmpty()
    )
}

fun RegisterParam.toRequest(): RegisterRequest {
    return RegisterRequest(
        name = name,
        email = email,
        password = password,
    )
}