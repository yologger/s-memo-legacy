package com.yologger.simple_memo.presentation.di

import com.yologger.simple_memo.presentation.screen.create.CreateViewModel
import com.yologger.simple_memo.presentation.screen.detail.DetailViewModel
import com.yologger.simple_memo.presentation.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
     viewModel { CreateViewModel(get()) }
     viewModel { HomeViewModel(get()) }
     viewModel { DetailViewModel(get()) }
}