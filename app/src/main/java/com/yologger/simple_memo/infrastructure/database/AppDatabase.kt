package com.yologger.simple_memo.infrastructure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yologger.simple_memo.infrastructure.entity.MemoEntity

@Database(entities = [MemoEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memoDao(): MemoDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "memo_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}