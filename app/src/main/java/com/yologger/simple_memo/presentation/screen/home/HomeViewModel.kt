package com.yologger.simple_memo.presentation.screen.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.screen.detail.DetailVMRoutingEvent
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
                            Log.d("TEST", "HomeViewModel: fetchAllMemos()")
                            Log.d("TEST", it.toString())
                            _memos = it.toMutableList()
                            _memosLiveData.setValue(_memos)
                        },
                        onError = { routingEvent.value = HomeVMRoutingEvent.UNKNOWN_ERROR },
                        onComplete = { }
                ).apply { disposables.add(this) }
    }

    fun deleteMemo(position: Int) {
        val memoId = _memos[position].id
        if (memoId != null) {
            memoRepository.deleteMemoById(memoId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onComplete = {
                                _memos.removeAt(position)
                                _memosLiveData.value = _memos
                                routingEvent.value = HomeVMRoutingEvent.DELETE_SUCCESS
                            },
                            onError = { routingEvent.value = HomeVMRoutingEvent.UNKNOWN_ERROR })
                    .apply { disposables.add(this) }
        }
    }

    fun openEdit() { routingEvent.value = HomeVMRoutingEvent.OPEN_DETAIL }

    fun openNewPost() { routingEvent.value = HomeVMRoutingEvent.OPEN_NEW_POST}

    fun openDetail() { routingEvent.value = HomeVMRoutingEvent.OPEN_DETAIL }
}