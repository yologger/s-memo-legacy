package com.yologger.simple_memo.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    val disposables by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}