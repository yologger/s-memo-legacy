package com.yologger.simple_memo.data.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import com.yologger.simple_memo.data.mapper.MemoEntityToMemoMapper
import com.yologger.simple_memo.infrastructure.database.MemoDao
import com.yologger.simple_memo.infrastructure.entity.MemoEntity
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.repository.MemoRepository
import io.reactivex.*
import io.reactivex.rxkotlin.cast
import io.reactivex.subjects.BehaviorSubject

class MemoRepositoryImpl
constructor(
        private val memoDao: MemoDao,
        private val memoEntityToMemoMapper: MemoEntityToMemoMapper
) : MemoRepository {

    override fun createMemo(title: String, content: String): Completable {
        val memoEntity = MemoEntity(title = title, content = content)
        return memoDao.insert(memoEntity)
    }

    override fun fetchAllMemos(): Flowable<List<Memo>> {
        return memoDao.getAllMemos().map { it.map(memoEntityToMemoMapper::map) }
    }

    override fun fetchMemo(userId: Int): Single<Memo> {
        return memoDao.getMemoById(userId).map { memoEntityToMemoMapper.map(memoEntity = it) }
    }

    override fun updateMemo(memo: Memo): Completable {
        val memoEntity = MemoEntity(id = memo.id!!, title = memo.title, content = memo.content)
        return memoDao.update(memoEntity)
    }

    override fun deleteMemo(memo: Memo): Completable {
        val memoEntity = MemoEntity(id = memo.id!!, title = memo.title, content = memo.content)
        return memoDao.delete(memoEntity)
    }

    override fun deleteMemoById(memoId: Int): Completable {
        return memoDao.deleteById(memoId)
    }
}