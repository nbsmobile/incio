package com.nbs.kmm.sample.data.rocketlaunch.local.dao

import com.nbs.kmm.sample.domain.rocketlaunch.model.Rocket
import com.nbs.kmm.sample.domain.rocketlaunch.model.RocketLaunch

interface RocketDao {
    fun clearDatabase()
    fun getAllLaunches(): List<RocketLaunch>
    fun createLaunches(launches: List<RocketLaunch>)
    fun insertRocket(rocket: Rocket)
    fun insertLaunch(launch: RocketLaunch)
}