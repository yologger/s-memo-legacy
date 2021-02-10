package com.yologger.simple_memo.presentation.screen

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.screen.create.CreateActivity
import com.yologger.simple_memo.presentation.screen.edit.EditActivity

class AppRouter
constructor(
    private val appActivity: AppActivity
) {
    private val navController: NavController by lazy { Navigation.findNavController(appActivity, R.id.activity_app_fcv) }

    fun openCreate() {
        // navController.navigate(R.id.action_homeFragment_to_createFragment)
        val nextIntent = Intent(appActivity, CreateActivity::class.java)
        appActivity.startActivity(nextIntent)
    }

    fun openEdit() {
        val nextIntent = Intent(appActivity, EditActivity::class.java)
        appActivity.startActivity(nextIntent)
    }
}