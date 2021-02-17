package com.yologger.simple_memo.presentation.screen.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.util.SingleLiveEvent
import com.yologger.simple_memo.presentation.util.transformer.AsyncTransformer
import com.yologger.simple_memo.presentation.util.transformer.FlowableAsyncTransformer
import com.yologger.simple_memo.presentation.util.transformer.ObservableAsyncTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class HomeViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    val routingEvent: SingleLiveEvent<HomeVMRoutingEvent> = SingleLiveEvent()

    private val _memos: MutableLiveData<List<Memo>> = MutableLiveData(listOf())
    val memos: LiveData<List<Memo>> = _memos

    fun fetchAllMemos() {
        memoRepository.fetchAllMemos()
            .take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _memos.setValue(it) },
                onError = {  },
                onComplete = {  }
            ).apply { disposables.add(this) }
    }
}