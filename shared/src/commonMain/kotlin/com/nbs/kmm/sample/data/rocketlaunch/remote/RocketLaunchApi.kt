package com.nbs.kmm.sample.data.rocketlaunch.remote

import com.nbs.kmm.sample.data.rocketlaunch.model.response.LaunchResponse
import com.nbs.kmm.sample.data.rocketlaunch.model.response.RocketResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RocketLaunchApi(private val httpClient: HttpClient): RocketLaunchApiClient {

    override suspend fun getAllLaunches(): List<LaunchResponse> {
        return httpClient.get( "launches/upcoming").body()
    }

    override suspend fun getRocket(rocketId: String): RocketResponse {
        return httpClient.get("rockets/$rocketId").body()
    }

}