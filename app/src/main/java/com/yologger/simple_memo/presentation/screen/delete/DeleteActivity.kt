package com.yologger.simple_memo.presentation.screen.delete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.base.BaseActivity
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.model.SelectableMemo
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeleteActivity : BaseActivity() {

    private val viewModel: DeleteViewModel by viewModel()

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: SelectableRecyclerViewAdapter
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        setup()
        setupBinding()
        setupToolbar()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchAllMemos()
    }

    private fun setup() {
        toolbar = findViewById(R.id.activity_delete_tb)
        recyclerView = findViewById(R.id.activity_delete_rv)
        coordinatorLayout = findViewById(R.id.activity_delete_cl)
    }

    private fun setupBinding() {
        viewModel.memosLiveData.observe(this, Observer { memos ->
            recyclerViewAdapter.updateMemos(memos)
        })

        viewModel.routingEvent.observe(this, Observer {
            when (it) {
                DeleteVMRoutingEvent.DELETE_SUCCESS -> {
                    Toast.makeText(this, getString(R.string.activity_delete_message_delete_success), Toast.LENGTH_SHORT).show()
                    finish()
                }
                DeleteVMRoutingEvent.DELETE_FAILURE -> { }
                DeleteVMRoutingEvent.CLOSE -> { finish() }
            }
        })
    }

    private fun setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.icon_close_filled_black_24)
        toolbar.setNavigationOnClickListener { viewModel.close() }
        toolbar.inflateMenu(R.menu.menu_activity_delete_toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.menu_activity_delete_toolbar_delete -> {
                    val builder = AlertDialog.Builder(coordinatorLayout.context)
                    builder.setMessage(getString(R.string.activity_delete_message_delete))
                    builder.setPositiveButton(getString(R.string.activity_delete_message_alert_ok)) { _, _ ->
                        recyclerViewAdapter.deleteMemos()
                    }
                    builder.setNegativeButton(getString(R.string.activity_delete_message_alert_cancel)) { _, _ ->
                    }
                    builder.show()
                }
                else -> {}
            }
            true
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = SelectableRecyclerViewAdapter(viewModel)
        recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
    }
}