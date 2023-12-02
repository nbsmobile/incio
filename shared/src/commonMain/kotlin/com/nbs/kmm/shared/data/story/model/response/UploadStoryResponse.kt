package com.nbs.kmm.shared.data.story.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadStoryResponse(
    @SerialName("error")
    val error: Boolean?,
    @SerialName("message")
    val message: String?
)