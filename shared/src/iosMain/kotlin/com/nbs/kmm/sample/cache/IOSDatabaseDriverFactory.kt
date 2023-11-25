package com.nbs.kmm.sample.cache

import co.touchlab.sqliter.DatabaseConfiguration
import com.nbs.kmm.shared.cache.DatabaseDriverFactory
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection

class IOSDatabaseDriverFactory(private val dbName: String, private val passphrase: String) :
    DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        val configuration = DatabaseConfiguration(
            name = dbName,
            version = AppDatabase.Schema.version,
            create = {connection ->
                wrapConnection(connection) { AppDatabase.Schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { AppDatabase.Schema.migrate(it, oldVersion, newVersion ) }
            },
            encryptionConfig = DatabaseConfiguration.Encryption(key = passphrase, rekey = passphrase)
        )

        return NativeSqliteDriver(configuration)
    }
}