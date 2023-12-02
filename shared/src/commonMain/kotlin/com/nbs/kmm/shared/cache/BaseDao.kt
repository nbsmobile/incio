package com.nbs.kmm.shared.cache

import com.nbs.kmm.sample.cache.AppDatabaseQueries

abstract class BaseDao {
    abstract val queries: AppDatabaseQueries
}