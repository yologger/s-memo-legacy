package com.yologger.simple_memo.presentation.repository

import com.yologger.simple_memo.infrastructure.entity.MemoEntity
import com.yologger.simple_memo.presentation.model.Memo
import io.reactivex.*

interface MemoRepository {
    fun createMemo(title: String = "", content: String =  "") : Completable
    fun fetchAllMemos() : Flowable<List<Memo>>
    fun fetchMemo(userId: Int) : Single<Memo>
    fun updateMemo(memo: Memo) : Completable
    fun deleteMemo(memo: Memo) : Completable
    fun deleteMemoById(memoId: Int): Completable
}