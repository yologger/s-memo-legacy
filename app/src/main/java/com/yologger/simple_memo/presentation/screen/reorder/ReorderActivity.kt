package com.yologger.simple_memo.presentation.screen.reorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yologger.simple_memo.R
import kotlinx.android.synthetic.main.activity_reorder.*

class ReorderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reorder)

        red_view.setOnClickListener {
            Log.d("TEST", "setOnClick!!!")
        }
    }
}