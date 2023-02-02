package com.nbs.kmm.sample.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

class IOSDatabaseDriverFactory(private val dbName: String, private val passphrase: String) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, dbName)
    }
}