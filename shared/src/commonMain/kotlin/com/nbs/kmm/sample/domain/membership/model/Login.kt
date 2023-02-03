package com.nbs.kmm.sample.domain.membership.model

import com.nbs.kmm.sample.utils.emptyString

data class Login(
    val token: String = emptyString(),
    val name: String = emptyString(),
    val userId: String = emptyString()
)

