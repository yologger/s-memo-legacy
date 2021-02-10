package com.yologger.simple_memo.application.di

import com.yologger.simple_memo.presentation.screen.AppActivity
import com.yologger.simple_memo.presentation.screen.AppRouter
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val appModule = module {
    loadKoinModules(module {
        factory { (appActivity: AppActivity) -> AppRouter(appActivity) }
    })
}