package com.nbs.kmm.shared.domain.rocketlaunch

import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch
import kotlinx.coroutines.flow.Flow

interface RocketLaunchUseCase {
    suspend fun getRocketLaunches(): Flow<List<RocketLaunch>>
    suspend fun removeRocketLaunches() : Flow<Boolean>
}