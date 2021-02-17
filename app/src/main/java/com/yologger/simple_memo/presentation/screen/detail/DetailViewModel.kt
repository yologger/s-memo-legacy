package com.yologger.simple_memo.presentation.screen.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yologger.simple_memo.presentation.base.BaseViewModel
import com.yologger.simple_memo.presentation.repository.MemoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DetailViewModel
constructor(
    private val memoRepository: MemoRepository
) : BaseViewModel() {

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
}