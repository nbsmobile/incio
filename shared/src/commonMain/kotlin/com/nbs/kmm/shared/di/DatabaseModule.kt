package com.nbs.kmm.shared.di

import com.nbs.kmm.sample.cache.AppDatabase
import com.nbs.kmm.shared.cache.DaoHelper
import com.nbs.kmm.shared.getPlatform
import org.koin.dsl.module

val databaseModule = module {
    single {
        AppDatabase(
            getPlatform().getDatabaseDriver(
                dbName = "test.db",
                passphrase = "NBSAwesome!"
            )
        )
    }

    single {
        DaoHelper(get())
    }
}