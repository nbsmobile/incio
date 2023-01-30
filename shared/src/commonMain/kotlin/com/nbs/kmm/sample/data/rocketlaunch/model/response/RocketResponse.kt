package com.nbs.kmm.sample.data.rocketlaunch.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketResponse(
    @SerialName("country")
    val country: String?,
    @SerialName("success_rate_pct")
    val successRatePct: Int?,
    @SerialName("wikipedia")
    val wikipedia: String?,
    @SerialName("first_flight")
    val firstFlight: String?,
    @SerialName("cost_per_launch")
    val costPerLaunch: Long?,
    @SerialName("stages")
    val stages: Int?,
    @SerialName("flickr_images")
    val flickrImages: List<String>?,
    @SerialName("type")
    val type: String?,
    @SerialName("boosters")
    val boosters: Int?,
    @SerialName("active")
    val active: Boolean?,
    @SerialName("name")
    val name: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("company")
    val company: String?
)