package com.yologger.simple_memo.data.di

import com.yologger.simple_memo.data.repository.MemoRepositoryImpl
import com.yologger.simple_memo.presentation.repository.MemoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MemoRepository> { MemoRepositoryImpl() }
}