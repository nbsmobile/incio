package com.nbs.kmm.sample.di

import com.nbs.kmm.sample.data.preference.PreferenceManager
import com.nbs.kmm.sample.data.preference.PreferenceManagerImpl
import com.nbs.kmm.sample.domain.account.AccountManager
import com.nbs.kmm.sample.domain.account.AccountManagerImpl
import com.nbs.kmm.sample.getPlatform
import org.koin.dsl.module

val preferenceModule = module {
    single { getPlatform().getEncryptedPreference("app-preference") }
    single<PreferenceManager> { PreferenceManagerImpl(get()) }
    single<AccountManager> { AccountManagerImpl(get(), get()) }
}
