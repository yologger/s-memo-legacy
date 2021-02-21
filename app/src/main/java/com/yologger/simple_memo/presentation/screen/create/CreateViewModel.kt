package com.yologger.simple_memo.presentation.screen.create

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.util.SingleLiveEvent
import com.yologger.simple_memo.presentation.util.transformer.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CreateViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    val routingEvent: SingleLiveEvent<CreateVMRoutingEvent> = SingleLiveEvent()

    val liveDataTitle = MutableLiveData("")
    val liveDataContent = MutableLiveData("")

    private var maxPosition = 0

    fun createMemo() {
        val title = liveDataTitle.value?.trimEnd()!!
        val content = liveDataContent.value?.trimEnd()!!
        if (title == "" && content == "") {
            return
        } else {
            maxPosition += 1
            memoRepository.createMemo(title = title, content = content, position = maxPosition)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onComplete = {
                                routingEvent.setValue(CreateVMRoutingEvent.CREATE_SUCCESS)
                            },
                            onError = { routingEvent.setValue(CreateVMRoutingEvent.CLOSE) })
                    .apply { disposables.add(this) }
        }
    }

    fun getMaxPosition() {
        memoRepository.getMaxPosition()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            maxPosition = it
                            Log.d("TEST", "maxPosition: ${maxPosition}")
                        },
                        onError = {
                            maxPosition = 0
                            Log.d("TEST", it.localizedMessage)
                        }
                )
    }
}