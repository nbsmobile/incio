package com.nbs.kmm.sample.android.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.nbs.kmm.sample.android.di.featureModule
import com.nbs.kmm.sample.android.di.rxModule
import com.nbs.kmm.sample.android.di.utilityModule
import com.nbs.kmm.sample.android.utils.ContextProvider
import com.nbs.kmm.sample.base.CoreApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import timber.log.Timber

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CoreApplication.initialize {
            androidLogger(Level.NONE)
            androidContext(applicationContext)
            modules(
                listOf(
                    rxModule,
                    utilityModule,
                    featureModule
                )
            )
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        ContextProvider.initialize(this)
    }
}