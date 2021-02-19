package com.yologger.simple_memo.presentation.screen.reorder

import android.util.Log
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.repository.MemoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReorderViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    var memos = mutableListOf<Memo>()

    fun fetchAllMemos() {
        memoRepository.fetchAllMemos()
            .take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Log.d("TEST", "ReorderViewModel: fetchAllMemos()")
                    memos = it.toMutableList()
                    Log.d("TEST", memos.toString())
                },
                onError = {},
                onComplete = {}
            ).apply { disposables.add(this) }
    }

    fun getSize() {

    }

    fun moveItem(from: Int, to: Int) {

    }
}