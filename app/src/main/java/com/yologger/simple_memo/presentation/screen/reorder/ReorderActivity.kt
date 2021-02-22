package com.yologger.simple_memo.presentation.screen.reorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.model.Memo
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReorderActivity : AppCompatActivity() {

    private val viewModel: ReorderViewModel by viewModel()

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ReorderableRecyclerViewAdapter
    private lateinit var itemMoveCallbackListener: ItemMoveCallbackListener
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reorder)
        setup()
        setupToolbar()
        setupRecyclerView()
        setupBinding()
    }

    private fun setup() {
        toolbar = findViewById(R.id.activity_reorder_tb)
        recyclerView = findViewById(R.id.activity_reorder_rv)
        viewModel.fetchAllMemos()
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_activity_reorder_toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.menu_activity_reorder_toolbar_done -> {
                    recyclerViewAdapter.updatePositions()
                }
            }
            true
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = ReorderableRecyclerViewAdapter(viewModel)
        recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        itemMoveCallbackListener = ItemMoveCallbackListener(adapter = recyclerViewAdapter)
        itemTouchHelper = ItemTouchHelper(itemMoveCallbackListener)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerViewAdapter.listener = object: ReorderableRecyclerViewAdapter.OnClickListener {
            override fun onClicked(viewHolder: ReorderableRecyclerViewAdapter.ViewHolder, position: Int) {
                itemTouchHelper.startDrag(viewHolder)
            }
        }
    }

    private fun setupBinding() {
        viewModel.memos.observe(this, Observer { memos ->
            recyclerViewAdapter.setupMemos(memos)
        })

        viewModel.routingEvent.observe(this, Observer {
            when(it) {
                ReorderVMRoutingEvent.UPDATE_POSITIONS_SUCCESS -> {
                    finish()
                }
                ReorderVMRoutingEvent.UPDATE_POSITIONS_FAILURE -> {

                }
            }
        })
    }
}
