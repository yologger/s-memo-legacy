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
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private val itemTouchHelper by lazy {
        val itemTouchCallback = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, 0) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val adapter = recyclerView.adapter as RecyclerViewAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                viewModel.moveItem(from, to)
//                adapter.moveItem(from, to)
//                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) { viewHolder?.itemView?.alpha = 0.5f }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                viewHolder?.itemView?.alpha = 1.0f
            }

            override fun isLongPressDragEnabled(): Boolean = false
        }
        ItemTouchHelper(itemTouchCallback)
    }

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
            when(it?.itemId) {
                R.id.menu_activity_reorder_toolbar_done -> { finish() }
            }
            true
        }
    }

    private fun setupRecyclerView() {
        // RecyclerView & RecyclerViewAdapter
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        // Reorder
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerViewAdapter.listener = object: RecyclerViewAdapter.ClickListener {
            override fun onTouch(position: Int, viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        }
    }

    private fun setupBinding() {
        viewModel.memos.observe(this, Observer { memos ->
            recyclerViewAdapter.updateMemos(memos.toMutableList())
        })

        viewModel.routingEvent.observe(this, Observer {
            when (it) {
                ReorderVMRoutingEvent.SWAP_SUCCESS -> {
                    Log.d("TEST" ,"SWAP_SUCCESS")
                }
                ReorderVMRoutingEvent.SWAP_FAILURE -> {
                    Log.d("TEST", "SWAP_FAILURE")
                }
            }
        })
    }
}

class RecyclerViewAdapter
constructor(
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val _memos = mutableListOf<Memo>()

    var listener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_reorder_memo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = _memos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = (holder as ViewHolder).bind(_memos[position], holder)

    fun updateMemos(memos: MutableList<Memo>) {
        _memos.clear()
        _memos.addAll(memos)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val fromItem = _memos[from]
//        Log.d("TEST", "from: ${from}")
//        Log.d("TEST", "to: ${to}")
//        Log.d("TEST", "from's position: ${_memos[from].position}")
//        Log.d("TEST", "to's position: ${_memos[to].position}")
        _memos.removeAt(from)
        if (to < from) {
            _memos.add(to, fromItem)
        } else {
            _memos.add(to-1, fromItem)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewTitle = itemView.findViewById<TextView>(R.id.item_activity_reorder_memo_tv_title)
        private val imageButtonReorder = itemView.findViewById<ImageButton>(R.id.item_activity_reorder_memo_btn_reorder)

        fun bind(memo: Memo, viewHolder: RecyclerView.ViewHolder) {
            textViewTitle.text = memo.title
            imageButtonReorder.setOnTouchListener { v, event ->
                listener?.onTouch(position, viewHolder)
                return@setOnTouchListener false
            }
        }
    }

    interface ClickListener {
        fun onTouch(position: Int, viewHolder: RecyclerView.ViewHolder)
    }
}