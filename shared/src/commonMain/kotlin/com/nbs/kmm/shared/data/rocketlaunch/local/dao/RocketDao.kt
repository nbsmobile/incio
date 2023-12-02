package com.nbs.kmm.shared.data.rocketlaunch.local.dao

import com.nbs.kmm.shared.domain.rocketlaunch.model.Rocket
import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch

interface RocketDao {
    fun clearDatabase()
    fun getAllLaunches(): List<RocketLaunch>
    fun createLaunches(launches: List<RocketLaunch>)
    fun insertRocket(rocket: Rocket)
    fun insertLaunch(launch: RocketLaunch)
}