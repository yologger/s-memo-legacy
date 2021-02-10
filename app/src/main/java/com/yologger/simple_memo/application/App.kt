package com.yologger.simple_memo.application

import android.app.Application
import android.content.res.Configuration
import com.yologger.simple_memo.application.di.appModule
import com.yologger.simple_memo.data.di.repositoryModule
import com.yologger.simple_memo.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}