package com.yologger.simple_memo.presentation.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yologger.simple_memo.R
import com.yologger.simple_memo.application.Constant

open class BaseActivity : AppCompatActivity() {
//
//    var currentTheme = LIGHT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val sharedPref = getSharedPreferences(PREF_THEME, Context.MODE_PRIVATE)
//        currentTheme = sharedPref.getInt(KEY_THEME, LIGHT)
//        setTheme(currentTheme)
    }

//    fun switchTheme() {
//        currentTheme = when (currentTheme) {
//            LIGHT -> DARK
//            DARK -> LIGHT
//            else -> -1
//        }
//        val sharedPref = getSharedPreferences(PREF_THEME, Context.MODE_PRIVATE) ?: return
//        with(sharedPref.edit()) {
//            putInt(KEY_THEME, currentTheme)
//            commit()
//        }
//    }
//
//    companion object {
//        private const val KEY_THEME = Constant.KEY_THEME
//        private const val PREF_THEME = Constant.PREF_THEME
//        private const val LIGHT = R.style.AppTheme_Light
//        private const val DARK = R.style.AppTheme_Dark
//    }
}