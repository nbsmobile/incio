package com.nbs.kmm.shared.data.rocketlaunch

import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch

interface RocketLaunchRepository {
    suspend fun getAllRocketLaunch() : Pair<String, List<RocketLaunch>>
    suspend fun saveAllRocketLaunch(rocketLaunch: List<RocketLaunch>)
    suspend fun removeAllRocketLaunch()
}