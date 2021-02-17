package com.yologger.simple_memo.presentation.util.transformer

import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CompletableAsyncTransformer<T> : CompletableTransformer {
    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}