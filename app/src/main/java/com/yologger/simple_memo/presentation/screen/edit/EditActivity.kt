package com.yologger.simple_memo.presentation.screen.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.yologger.simple_memo.R
import com.yologger.simple_memo.databinding.ActivityEditBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditActivity : AppCompatActivity() {

    private val viewModel: EditViewModel by viewModel()
    private lateinit var binding: ActivityEditBinding

    lateinit var toolbar: Toolbar
    lateinit var editTextTitle: EditText
    lateinit var editTextContent: EditText
    lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setup()
        setupBinding()
        setupToolbar()
    }

    private fun setup() {
        coordinatorLayout = findViewById(R.id.activity_edit_cl)
        editTextTitle = findViewById(R.id.activity_edit_tv_title)
        editTextContent = findViewById(R.id.activity_edit_tv_content)
        val memoId = intent.getIntExtra("memoId", 0)
        viewModel.fetchMemo(memoId)
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.activity_edit_toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_close_filled_black_24)
        toolbar.setNavigationOnClickListener { viewModel.cancel() }
        toolbar.inflateMenu(R.menu.menu_activity_edit_toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.menu_activity_edit_toolbar_save -> {
                    val memoId = intent.getIntExtra("memoId", 0)
                    viewModel.updateMemo(memoId)
                }
                else -> { viewModel.cancel() }
            }
            true
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.routingEvent.observe(this, Observer {
            when (it) {
                EditVMRoutingEvent.SAVE_AND_CLOSE -> {
                    Toast.makeText(this, "UPDATED SUCCESSFULLY", Toast.LENGTH_SHORT).show()
                    finish()
                }
                EditVMRoutingEvent.CANCEL -> { finish() }
                EditVMRoutingEvent.UNKNOWN_ERROR -> {
                    Snackbar.make(coordinatorLayout, "UNKNOWN ERROR", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}