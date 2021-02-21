package com.yologger.simple_memo.infrastructure.database

import androidx.room.*
import com.yologger.simple_memo.infrastructure.entity.MemoEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class MemoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(memoEntity: MemoEntity): Completable

    @Query("SELECT * FROM MemoEntity ORDER BY position ASC")
    abstract fun getAllMemos(): Flowable<List<MemoEntity>>

    @Query("SELECT * FROM MemoEntity WHERE id = :memoId")
    abstract fun getMemoById(memoId: Int): Single<MemoEntity>

    @Update
    abstract fun update(memoEntity: MemoEntity): Completable

    @Delete
    abstract fun delete(memoEntity: MemoEntity): Completable

    @Query("DELETE FROM MemoEntity WHERE id = :memoId")
    abstract fun deleteById(memoId: Int): Completable

    @Query("SELECT MAX(position) FROM MemoEntity")
    abstract fun getMaxPosition() : Single<Int>

    @Query("UPDATE MemoEntity SET position = :position WHERE id = :memoId")
    abstract fun updatePosition(memoId: Int, position: Int): Completable

//    @Transaction
//    open fun swapPosition(from: MemoEntity, to: MemoEntity) : Completable {
//        updatePosition(from.id, to.position)
//        updatePosition(to.id, from.position)
//    }
}