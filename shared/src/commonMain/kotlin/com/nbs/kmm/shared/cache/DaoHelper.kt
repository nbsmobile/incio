package com.nbs.kmm.shared.cache

import com.nbs.kmm.sample.cache.AppDatabase
import com.nbs.kmm.shared.data.rocketlaunch.local.RocketDaoImpl
import com.nbs.kmm.shared.data.rocketlaunch.local.dao.RocketDao

class DaoHelper(private val appDatabase: AppDatabase){
    fun rocketDao(): RocketDao = RocketDaoImpl(appDatabase)
}