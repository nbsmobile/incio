package com.nbs.kmm.shared.data.story.model.response

import com.nbs.kmm.shared.data.base.BaseResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryDataResponse(
    @SerialName("error")
    override val isError: Boolean?,
    @SerialName("message")
    override val message: String?,
    @SerialName("listStory")
    val stories: List<StoryResponse>
): BaseResponse()

@Serializable
data class StoryResponse(
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("lon")
    val lon: Double?,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("lat")
    val lat: Double?
)