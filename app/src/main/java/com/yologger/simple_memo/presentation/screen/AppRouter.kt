package com.yologger.simple_memo.presentation.screen

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.screen.create.CreateActivity
import com.yologger.simple_memo.presentation.screen.delete.DeleteActivity
import com.yologger.simple_memo.presentation.screen.detail.DetailActivity
import com.yologger.simple_memo.presentation.screen.reorder.ReorderActivity

class AppRouter
constructor(
    private val appActivity: AppActivity
) {
    fun openCreate() {
        val nextIntent = Intent(appActivity, CreateActivity::class.java)
        appActivity.startActivity(nextIntent)
    }

    fun openEdit() {
        val nextIntent = Intent(appActivity, ReorderActivity::class.java)
        appActivity.startActivity(nextIntent)
    }

    fun openDetail(memoId: Int, memoPosition: Int) {
        val nextIntent = Intent(appActivity, DetailActivity::class.java)
        nextIntent.putExtra("memoId", memoId)
        nextIntent.putExtra("memoPosition", memoPosition)
        appActivity.startActivity(nextIntent)
    }

    fun openDelete() {
        val nextIntent = Intent(appActivity, DeleteActivity::class.java)
        appActivity.startActivity(nextIntent)
    }
}