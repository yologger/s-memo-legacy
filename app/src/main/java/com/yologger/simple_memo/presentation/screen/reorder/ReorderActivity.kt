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
                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)
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
                R.id.menu_activity_reorder_toolbar_done -> {
                    Log.d("TEST", "DONE")
                    finish()
                }
            }
            true
        }
    }

    private fun setupRecyclerView() {
        // RecyclerView & RecyclerViewAdapter
        recyclerViewAdapter = RecyclerViewAdapter(viewModel)
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
}

class RecyclerViewAdapter
constructor(
    private val viewModel: ReorderViewModel
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

//    val memos = mutableListOf<Memo>(
//        Memo(1, "title1", "content1"),
//        Memo(2, "title2", "content2"),
//        Memo(3, "title3", "content3"),
//        Memo(4, "title4", "content4"),
//        Memo(5, "title5", "content5"),
//        Memo(6, "title6", "content6"),
//        Memo(7, "title7", "content7"),
//        Memo(8, "title8", "content8"),
//        Memo(9, "title9", "content9"),
//        Memo(10, "title10", "content10"),
//        Memo(11, "title11", "content11"),
//        Memo(12, "title12", "content12"),
//        Memo(13, "title13", "content13"),
//        Memo(14, "title14", "content14")
//    )

    var listener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_reorder_memo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = viewModel.memos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = (holder as ViewHolder).bind(viewModel.memos[position], holder)

    fun moveItem(from: Int, to: Int) {
        val fromItem = viewModel.memos[from]
        viewModel.memos.removeAt(from)
        if (to < from) {
            viewModel.memos.add(to, fromItem)
        } else {
            viewModel.memos.add(to-1, fromItem)
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