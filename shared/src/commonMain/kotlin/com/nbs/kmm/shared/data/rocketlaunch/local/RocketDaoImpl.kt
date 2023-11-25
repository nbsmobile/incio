package com.nbs.kmm.shared.data.rocketlaunch.local

import com.nbs.kmm.sample.cache.AppDatabase
import com.nbs.kmm.sample.cache.AppDatabaseQueries
import com.nbs.kmm.shared.cache.BaseDao
import com.nbs.kmm.shared.data.rocketlaunch.local.dao.RocketDao
import com.nbs.kmm.shared.domain.rocketlaunch.model.Links
import com.nbs.kmm.shared.domain.rocketlaunch.model.Rocket
import com.nbs.kmm.shared.domain.rocketlaunch.model.RocketLaunch

class RocketDaoImpl(database: AppDatabase) : BaseDao(), RocketDao {

    override val queries: AppDatabaseQueries = database.appDatabaseQueries

    override fun clearDatabase() {
        queries.transaction {
            queries.removeAllRockets()
            queries.removeAllRocketLaunches()
        }
    }

    override fun getAllLaunches(): List<RocketLaunch> {
        return queries.selectAllRocketLaunchesInfo(::mapLaunchSelecting).executeAsList()
    }

    override fun createLaunches(launches: List<RocketLaunch>) {
        queries.transaction {
            launches.forEach { launch ->
                val rocket = queries.selectRocketById(launch.rocket.id).executeAsOneOrNull()
                if (rocket == null) {
                    insertRocket(launch.rocket)
                }

                insertLaunch(launch)
            }
        }
    }

    override fun insertRocket(rocket: Rocket) {
        queries.insertRocket(
            id = rocket.id,
            name = rocket.name,
            type = rocket.type
        )
    }

    override fun insertLaunch(launch: RocketLaunch) {
        queries.insertRocketLaunch(
            flightNumber = launch.flightNumber.toLong(),
            missionName = launch.missionName,
            launchYear = launch.launchYear,
            rocketId = launch.rocket.id,
            details = launch.details,
            launchSuccess = launch.launchSuccess,
            launchDateUTC = launch.launchDateUTC,
            missionPatchUrl = launch.links.missionPatchUrl,
            articleUrl = launch.links.articleUrl
        )
    }

    private fun mapLaunchSelecting(
        flightNumber: Long,
        missionName: String,
        launchYear: Int,
        rocketId: String,
        details: String?,
        launchSuccess: Boolean?,
        launchDateUTC: String,
        missionPatchUrl: String?,
        articleUrl: String?,
        rocket_id: String?,
        name: String?,
        type: String?
    ): RocketLaunch {
        return RocketLaunch(
            flightNumber = flightNumber.toInt(),
            missionName = missionName,
            launchYear = launchYear,
            details = details.orEmpty(),
            launchDateUTC = launchDateUTC,
            launchSuccess = launchSuccess ?: false,
            rocket = Rocket(
                id = rocketId,
                name = name!!,
                type = type!!
            ),
            links = Links(
                missionPatchUrl = missionPatchUrl.orEmpty(),
                articleUrl = articleUrl.orEmpty()
            )
        )
    }

}