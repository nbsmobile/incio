package com.nbs.kmm.shared.domain.membership.model

import com.nbs.kmm.shared.utils.emptyString

data class Login(
    val token: String = emptyString(),
    val name: String = emptyString(),
    val userId: String = emptyString()
)

