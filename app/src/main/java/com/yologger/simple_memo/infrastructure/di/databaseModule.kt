package com.yologger.simple_memo.infrastructure.di

import com.yologger.simple_memo.infrastructure.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.buildDatabase(androidApplication()) }

    single { get<AppDatabase>().memoDao() }
}