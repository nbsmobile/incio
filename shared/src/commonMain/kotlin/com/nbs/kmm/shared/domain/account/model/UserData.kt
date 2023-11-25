package com.nbs.kmm.shared.domain.account.model

import com.nbs.kmm.shared.utils.emptyString
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val userId: String = emptyString(),
    val name: String = emptyString()
)
