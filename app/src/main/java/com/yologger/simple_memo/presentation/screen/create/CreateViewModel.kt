package com.yologger.simple_memo.presentation.screen.create

import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.repository.MemoRepository
import com.yologger.simple_memo.presentation.util.SingleLiveEvent

class CreateViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

    var routingEvent: SingleLiveEvent<CreateVMRoutingEvent> = SingleLiveEvent()

    val liveDataTitle = MutableLiveData("")
    val liveDataContent = MutableLiveData("")

    fun createMemo() {
        val title = liveDataTitle.value?.trimEnd()!!
        val content = liveDataContent.value?.trimEnd()!!
        if (title == "" && content == "") {
            return
        } else {
            memoRepository.createMemo()
            // routingEvent.setValue(CreateVMRoutingEvent.CLOSE)
        }
    }
}