package com.yologger.simple_memo.presentation.screen.reorder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.infrastructure.entity.MemoEntity
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ReorderViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    val routingEvent: SingleLiveEvent<ReorderVMRoutingEvent> = SingleLiveEvent()

    private var _memos = mutableListOf<Memo>()
    val memos: MutableLiveData<List<Memo>> = MutableLiveData(listOf())

    fun fetchAllMemos() {
        memoRepository.fetchAllMemos()
            .take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _memos = it.toMutableList()
                    memos.value = _memos
                },
                onError = {},
                onComplete = {}
            ).apply { disposables.add(this) }
    }

    fun moveItem(from: Int, to: Int) {
//        Log.d("TEST", "from: ${from}")
//        Log.d("TEST", "to: ${to}")
        val _from = _memos[from]
        val _to = _memos[to]
        Log.d("TEST", "FROM MEMO: ${_from.toString()}")
        Log.d("TEST", "TO MEMOL ${_to.toString()}")
        memoRepository.swapPositions(_from, _to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {

                        },
                        onError = {}
                ).apply { disposables.add(this) }
    }

    fun swapPositions(from: Memo, to: Memo) {
    }
}