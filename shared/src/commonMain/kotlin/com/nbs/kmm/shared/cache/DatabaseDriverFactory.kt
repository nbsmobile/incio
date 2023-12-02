package com.nbs.kmm.shared.cache

import com.squareup.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}