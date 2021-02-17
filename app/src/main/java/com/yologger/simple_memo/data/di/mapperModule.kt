package com.yologger.simple_memo.data.di

import com.yologger.simple_memo.data.mapper.MemoEntityToMemoMapper
import org.koin.dsl.module

val mapperModule = module {
    factory<MemoEntityToMemoMapper> { MemoEntityToMemoMapper() }
}
