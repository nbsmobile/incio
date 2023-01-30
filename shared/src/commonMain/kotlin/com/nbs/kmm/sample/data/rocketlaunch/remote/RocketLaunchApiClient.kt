package com.nbs.kmm.sample.data.rocketlaunch.remote

import com.nbs.kmm.sample.data.rocketlaunch.model.response.LaunchResponse
import com.nbs.kmm.sample.data.rocketlaunch.model.response.RocketResponse

interface RocketLaunchApiClient {
    suspend fun getAllLaunches() : List<LaunchResponse>
    suspend fun getRocket(rocketId: String) : RocketResponse
}