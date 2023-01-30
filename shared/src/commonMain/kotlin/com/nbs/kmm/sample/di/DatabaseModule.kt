package com.nbs.kmm.sample.di

import com.nbs.kmm.sample.cache.AppDatabase
import com.nbs.kmm.sample.cache.DaoHelper
import com.nbs.kmm.sample.getPlatform
import org.koin.dsl.module

val databaseModule = module {
    single {
        AppDatabase(getPlatform().getDatabaseDriver())
    }

    single {
        DaoHelper(get())
    }
}