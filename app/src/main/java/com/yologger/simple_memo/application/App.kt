package com.yologger.simple_memo.application

import android.app.Application
import android.content.res.Configuration
import com.yologger.simple_memo.application.di.appModule
import com.yologger.simple_memo.data.di.mapperModule
import com.yologger.simple_memo.data.di.repositoryModule
import com.yologger.simple_memo.infrastructure.di.databaseModule
import com.yologger.simple_memo.presentation.di.viewModelModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setupTheme()
        setupRealm()
        setupKoin()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun setupTheme() {
        val currentTheme = ThemeManager.getCurrentTheme(context = applicationContext)
        when (currentTheme) {
            ThemeManager.ThemeMode.DARK -> { ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.DARK) }
            ThemeManager.ThemeMode.LIGHT -> { ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.LIGHT) }
            ThemeManager.ThemeMode.DEFAULT -> { ThemeManager.applyTheme(applicationContext, ThemeManager.ThemeMode.DEFAULT) }
        }
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    mapperModule,
                    repositoryModule,
                    databaseModule
                )
            )
        }
    }

    private fun setupRealm() {
        Realm.init(this@App)
        val config = RealmConfiguration.Builder()
            .name("memos")
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config);

    }
}