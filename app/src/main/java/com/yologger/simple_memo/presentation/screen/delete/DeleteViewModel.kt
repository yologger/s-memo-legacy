package com.yologger.simple_memo.presentation.screen.delete

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.screen.home.HomeVMRoutingEvent
import com.yologger.simple_memo.presentation.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DeleteViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    val routingEvent: SingleLiveEvent<DeleteVMRoutingEvent> = SingleLiveEvent()

    private var _memos = mutableListOf<Memo>()
    private val _memosLiveData: MutableLiveData<List<Memo>> = MutableLiveData(listOf())
    val memosLiveData: LiveData<List<Memo>> = _memosLiveData

    fun fetchAllMemos() {
        memoRepository.fetchAllMemos()
            .take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _memos = it.toMutableList()
                    _memosLiveData.setValue(_memos)
                },
                onError = { },
                onComplete = { }
            ).apply { disposables.add(this) }
    }

    fun deleteMemos(memos: List<Memo>) {
        memoRepository.deleteMemos(memos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = { routingEvent.value = DeleteVMRoutingEvent.DELETE_SUCCESS },
                        onError = { routingEvent.value = DeleteVMRoutingEvent.DELETE_FAILURE }
                ).apply { disposables.add(this) }
    }

    fun close() {
        routingEvent.value = DeleteVMRoutingEvent.CLOSE
    }
}