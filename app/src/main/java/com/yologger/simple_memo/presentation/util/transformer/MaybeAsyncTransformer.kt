package com.yologger.simple_memo.presentation.util.transformer

import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.MaybeTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MaybeAsyncTransformer<T>: MaybeTransformer<T, T> {
    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}