package com.yologger.simple_memo.presentation.screen.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.yologger.simple_memo.R
import com.yologger.simple_memo.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    lateinit var toolbar: Toolbar
    lateinit var editTextTitle: EditText
    lateinit var editTextContent: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setup()
        setupBinding()
        setupToolbar()
        setupGesture()
    }

    private fun setup() {
        editTextTitle = findViewById(R.id.activity_detail_et_title)
        editTextContent = findViewById(R.id.activity_detail_et_content)
        val memoId = intent.getIntExtra("memoId", 0)
        viewModel.fetchMemo(memoId)
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewModel = viewModel
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.activity_detail_toolbar)
        toolbar.setNavigationIcon(R.drawable.icon_close_filled_black_24)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.inflateMenu(R.menu.menu_activity_detail_toolbar)
        toolbar.setOnMenuItemClickListener {
            when(it?.itemId) {
                R.id.menu_activity_detail_toolbar_delete -> {}
                R.id.menu_activity_detail_toolbar_edit -> {}
            }
            true
        }
    }

    private fun setupGesture() {

    }
}