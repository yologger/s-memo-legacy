package com.yologger.simple_memo.presentation.screen.create

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.yologger.simple_memo.R
import com.yologger.simple_memo.databinding.ActivityCreateBinding
import com.yologger.simple_memo.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateActivity : BaseActivity() {

    private val viewModel: CreateViewModel by viewModel()
    private lateinit var binding: ActivityCreateBinding

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        setupBinding()
        setupToolbar()
        // viewModel.getSize()
        setTheme(R.style.AppTheme_Dark)
        viewModel.getMaxPosition()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.routingEvent.observe(this, Observer {
            when (it) {
                CreateVMRoutingEvent.CREATE_SUCCESS -> {
                    Toast.makeText(this, "CREATED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                    finish()
                }
                CreateVMRoutingEvent.CLOSE -> { finish() }
            }
        })
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.activity_create_toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_close_filled_black_24)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.menu_activity_create_toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.menu_activity_create_toolbar_done -> { viewModel.createMemo() }
                else -> { finish() }
            }
            true
        }
    }
}