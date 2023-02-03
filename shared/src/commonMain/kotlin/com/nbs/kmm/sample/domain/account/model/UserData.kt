package com.nbs.kmm.sample.domain.account.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val userId: String = "",
    val name: String = ""
)
