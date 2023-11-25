package com.nbs.kmm.shared.data.rocketlaunch.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchResponse(
    @SerialName("links")
    val links: LinkResponse?,
    @SerialName("name")
    val name: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("rocket")
    val rocket: String?,
    @SerialName("tdb")
    val tdb: Boolean?,
    @SerialName("capsules")
    val capsules: List<String>?,
    @SerialName("success")
    val success: Boolean?,
    @SerialName("launchpad")
    val launchpad: String?,
    @SerialName("date_local")
    val dateLocal: String?,
    @SerialName("static_fire_date_unix")
    val staticFireDateUnix: Long?,
    @SerialName("details")
    val details: String?,
    @SerialName("flight_number")
    val flightNumber: Int?,
    @SerialName("date_utc")
    val dateUtc: String?,
    @SerialName("upcoming")
    val upcoming: Boolean?,
    @SerialName("auto_update")
    val autoUpdate: Boolean?,
    @SerialName("date_precision")
    val datePrecision: String?,
    @SerialName("net")
    val net: Boolean?,
    @SerialName("date_unix")
    val dateUnix: Long?,
    @SerialName("static_fire_date_utc")
    val staticFireDateUtc: String?,
    @SerialName("payloads")
    val payloads: List<String>?,
    @SerialName("window")
    val window: Int?
)