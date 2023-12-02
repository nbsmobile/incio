package com.nbs.kmm.shared.domain.rocketlaunch

import com.nbs.kmm.shared.data.rocketlaunch.RocketLaunchRepository
import com.nbs.kmm.shared.domain.base.execute
import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class RocketLaunchInteractor(
    private val rocketLaunchRepository: RocketLaunchRepository
) : RocketLaunchUseCase {

    override suspend fun getRocketLaunches(): Flow<List<RocketLaunch>> {
        return execute {
            rocketLaunchRepository.getAllRocketLaunch()
        }.onEach {
            if (it.first == "Network") {
                rocketLaunchRepository.saveAllRocketLaunch(it.second)
            }
        }.map {
            it.second
        }
    }

    override suspend fun removeRocketLaunches() : Flow<Boolean> {
        return execute {
            rocketLaunchRepository.removeAllRocketLaunch()
        }.map {
            true
        }
    }
}