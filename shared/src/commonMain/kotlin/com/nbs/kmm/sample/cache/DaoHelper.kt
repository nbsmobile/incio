package com.nbs.kmm.sample.cache

import com.nbs.kmm.sample.data.rocketlaunch.local.RocketDaoImpl
import com.nbs.kmm.sample.data.rocketlaunch.local.dao.RocketDao

class DaoHelper(private val appDatabase: AppDatabase){
    fun rocketDao(): RocketDao = RocketDaoImpl(appDatabase)
}