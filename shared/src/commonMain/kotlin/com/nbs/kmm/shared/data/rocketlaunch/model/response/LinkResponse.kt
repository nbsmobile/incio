package com.nbs.kmm.shared.data.rocketlaunch.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkResponse(
    @SerialName("wikipedia")
    val wikipedia: String?,
    @SerialName("webcast")
    val webcast: String?,
    @SerialName("youtube_id")
    val youtubeId: String?,
    @SerialName("presskit")
    val presskit: String?,
    @SerialName("article")
    val article: String?,
    @SerialName("patch")
    val patch: PatchResponse?
)

@Serializable
data class PatchResponse(
    @SerialName("small")
    val small: String?,
    @SerialName("large")
    val large: String?
)