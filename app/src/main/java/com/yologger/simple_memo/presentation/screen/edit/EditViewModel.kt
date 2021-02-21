package com.yologger.simple_memo.presentation.screen.edit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class EditViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    val routingEvent: SingleLiveEvent<EditVMRoutingEvent> = SingleLiveEvent()

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
                onError = {

                }
            )
            .apply { disposables.add(this) }
    }

    fun updateMemo(memoId: Int, memoPosition: Int) {
        val _title = title.value?.trimEnd()!!
        val _content = content.value?.trimEnd()!!
        if (_title == "" && _content == "") {
            return
        } else {
            val memo = Memo(id = memoId, title = _title, content = _content, position = memoPosition)
            memoRepository.updateMemo(memo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onComplete = { routingEvent.value = EditVMRoutingEvent.SAVE_AND_CLOSE },
                    onError = { routingEvent.value = EditVMRoutingEvent.UNKNOWN_ERROR })
                .apply { disposables.add(this) }
        }
    }

    fun cancel() {
        routingEvent.value = EditVMRoutingEvent.CANCEL
    }
}