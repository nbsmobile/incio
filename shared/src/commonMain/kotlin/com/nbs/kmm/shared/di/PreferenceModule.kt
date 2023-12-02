package com.nbs.kmm.shared.di

import com.nbs.kmm.shared.data.preference.PreferenceManager
import com.nbs.kmm.shared.data.preference.PreferenceManagerImpl
import com.nbs.kmm.shared.domain.account.AccountManager
import com.nbs.kmm.shared.domain.account.AccountManagerImpl
import com.nbs.kmm.shared.getPlatform
import org.koin.dsl.module

val preferenceModule = module {
    single { getPlatform().getEncryptedPreference("app-preference") }
    single<PreferenceManager> { PreferenceManagerImpl(get()) }
    single<AccountManager> { AccountManagerImpl(get(), get()) }
}
