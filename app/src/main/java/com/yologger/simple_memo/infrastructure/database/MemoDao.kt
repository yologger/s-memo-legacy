package com.yologger.simple_memo.infrastructure.database

import androidx.room.*
import com.yologger.simple_memo.infrastructure.entity.MemoEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MemoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memoEntity: MemoEntity): Completable

    @Query("SELECT * FROM MemoEntity")
    fun getAllMemos(): Flowable<List<MemoEntity>>

    @Query("SELECT * FROM MemoEntity WHERE id = :userId")
    fun getMemoById(userId: Int): Single<MemoEntity>

    @Update
    fun update(memoEntity: MemoEntity): Completable

    @Delete
    fun delete(memoEntity: MemoEntity): Completable

    @Query("DELETE FROM MemoEntity WHERE id = :memoId")
    fun deleteById(memoId: Int): Completable

}