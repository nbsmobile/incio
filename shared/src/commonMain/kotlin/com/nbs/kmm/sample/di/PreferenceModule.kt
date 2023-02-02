package com.nbs.kmm.sample.di

import com.nbs.kmm.sample.data.preference.PreferenceManager
import com.nbs.kmm.sample.data.preference.PreferenceManagerImpl
import com.nbs.kmm.sample.domain.account.AccountManager
import com.nbs.kmm.sample.domain.account.AccountManagerImpl
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val preferenceModule = module {
    single { Settings() }
    single<PreferenceManager> { PreferenceManagerImpl(get()) }
    single<AccountManager> { AccountManagerImpl(get(), get()) }
}
