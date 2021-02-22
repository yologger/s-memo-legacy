package com.yologger.simple_memo.presentation.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.base.BaseActivity
import com.yologger.simple_memo.presentation.util.setupWithNavController
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AppActivity : BaseActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var currentNavController: LiveData<NavController>? = null
    val router: AppRouter by inject { parametersOf(this@AppActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }

    override fun onStart() {
        super.onStart()
        setup()
        setupBottomNavigationView()
    }

    override fun onStop() {
        super.onStop()
        currentNavController = null
    }

    private fun setup() {
        this.bottomNavigationView = findViewById(R.id.activity_app_bnv)
    }

    private fun setupBottomNavigationView() {
        val navGraphIds = listOf(R.navigation.graph_home, R.navigation.graph_settings)
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.activity_app_fcv,
            intent = intent
        )
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}