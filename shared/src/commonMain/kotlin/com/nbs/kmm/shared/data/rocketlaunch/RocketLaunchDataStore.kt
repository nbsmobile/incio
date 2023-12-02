package com.nbs.kmm.shared.data.rocketlaunch

import com.nbs.kmm.shared.cache.DaoHelper
import com.nbs.kmm.shared.data.rocketlaunch.remote.RocketLaunchApiClient
import com.nbs.kmm.shared.domain.rocketlaunch.mapper.map
import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch

class RocketLaunchDataStore(
    private val rocketLaunchApiClient: RocketLaunchApiClient,
    private val daoHelper: DaoHelper
) : RocketLaunchRepository {

    override suspend fun getAllRocketLaunch(): Pair<String, List<RocketLaunch>> {
        var source = "Local"
        val data = daoHelper.rocketDao().getAllLaunches().ifEmpty {
            rocketLaunchApiClient.getAllLaunches().map {
                source = "Network"
                val rocket = rocketLaunchApiClient.getRocket(it.rocket.orEmpty())
                it.map(rocket)
            }
        }
        return Pair(source, data)
    }

    override suspend fun saveAllRocketLaunch(rocketLaunch: List<RocketLaunch>) {
        daoHelper.rocketDao().createLaunches(rocketLaunch)
    }

    override suspend fun removeAllRocketLaunch() {
        daoHelper.rocketDao().clearDatabase()
    }
}