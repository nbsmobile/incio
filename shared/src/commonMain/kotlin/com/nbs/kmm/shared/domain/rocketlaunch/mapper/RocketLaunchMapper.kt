package com.nbs.kmm.shared.domain.rocketlaunch.mapper

import com.nbs.kmm.shared.data.rocketlaunch.model.response.LaunchResponse
import com.nbs.kmm.shared.data.rocketlaunch.model.response.LinkResponse
import com.nbs.kmm.shared.data.rocketlaunch.model.response.RocketResponse
import com.nbs.kmm.shared.domain.rocketlaunch.model.Links
import com.nbs.kmm.shared.domain.rocketlaunch.model.Rocket
import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch
import com.nbs.kmm.shared.utils.getYearFromEpoch
import com.nbs.kmm.shared.utils.ext.orZero

fun RocketResponse.map() = Rocket(
    id = id.orEmpty(),
    name = name.orEmpty(),
    type = type.orEmpty()
)

fun LinkResponse.map() = Links(
    missionPatchUrl = patch?.large.orEmpty(),
    articleUrl = article.orEmpty()
)

fun LaunchResponse.map(rocket: RocketResponse) = RocketLaunch(
    flightNumber = flightNumber.orZero(),
    links = links?.map() ?: Links(),
    rocket = rocket.map(),
    missionName = name.orEmpty(),
    launchYear = dateUnix.orZero().getYearFromEpoch(),
    launchDateUTC = dateUtc.orEmpty(),
    details = details.orEmpty(),
    launchSuccess = success ?: false
)