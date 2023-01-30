package com.nbs.kmm.sample.domain.rocketlaunch

import com.nbs.kmm.sample.domain.rocketlaunch.model.RocketLaunch
import kotlinx.coroutines.flow.Flow

interface RocketLaunchUseCase {
    suspend fun getRocketLaunches(): Flow<List<RocketLaunch>>
    suspend fun removeRocketLaunches() : Flow<Boolean>
}