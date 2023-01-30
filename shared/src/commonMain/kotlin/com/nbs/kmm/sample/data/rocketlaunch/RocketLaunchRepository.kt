package com.nbs.kmm.sample.data.rocketlaunch

import com.nbs.kmm.sample.domain.rocketlaunch.model.RocketLaunch

interface RocketLaunchRepository {
    suspend fun getAllRocketLaunch() : Pair<String, List<RocketLaunch>>
    suspend fun saveAllRocketLaunch(rocketLaunch: List<RocketLaunch>)
    suspend fun removeAllRocketLaunch()
}