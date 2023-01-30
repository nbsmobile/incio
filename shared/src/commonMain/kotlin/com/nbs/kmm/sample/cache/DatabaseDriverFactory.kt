package com.nbs.kmm.sample.cache

import com.squareup.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}