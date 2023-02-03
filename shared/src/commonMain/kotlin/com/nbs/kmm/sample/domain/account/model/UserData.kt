package com.nbs.kmm.sample.domain.account.model

import com.nbs.kmm.sample.utils.emptyString
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val userId: String = emptyString(),
    val name: String = emptyString()
)
