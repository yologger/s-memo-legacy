package com.yologger.simple_memo.presentation.screen.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DetailViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    val routingEvent: SingleLiveEvent<DetailVMRoutingEvent> = SingleLiveEvent()

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    fun fetchMemo(memoId: Int) {
        memoRepository.fetchMemo(memoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { memo ->
                    title.value = memo.title
                    content.value = memo.content
                },
                onError = {}
            )
            .apply { disposables.add(this) }
    }

    fun showToast() {
        routingEvent.value = DetailVMRoutingEvent.SHOW_TOAST
    }

    fun openEdit() {
        routingEvent.value = DetailVMRoutingEvent.OPEN_EDIT
    }

    fun showDeleteDialog() {
        routingEvent.value = DetailVMRoutingEvent.SHOW_DELETE_DIALOG
    }

    fun deletePost(memoId: Int) {
        memoRepository.deleteMemoById(memoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { routingEvent.value = DetailVMRoutingEvent.DELETE_AND_CLOSE },
                onError = { routingEvent.value = DetailVMRoutingEvent.UNKNOWN_ERROR })
            .apply { disposables.add(this) }
    }
}