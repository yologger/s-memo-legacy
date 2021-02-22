package com.yologger.simple_memo.presentation.screen.delete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.model.Memo
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeleteActivity : AppCompatActivity() {

    private val viewModel: DeleteViewModel by viewModel()

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

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
    }

    private fun setupBinding() {
        viewModel.memosLiveData.observe(this, Observer { memos ->
            recyclerViewAdapter.update(memos)
        })
    }

    private fun setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.icon_close_filled_black_24)
        toolbar.setNavigationOnClickListener {  }
        toolbar.inflateMenu(R.menu.menu_activity_delete_toolbar)
        toolbar.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.menu_activity_delete_toolbar_delete -> { finish() }
                else -> {}
            }
            true
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
    }

    inner class RecyclerViewAdapter
    constructor(
        private var memos: MutableList<Memo> = mutableListOf()
    ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_delete_memo, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = memos.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            return (holder as ViewHolder).bind(memos[position])
        }

        fun update(memos: List<Memo>) {
            this.memos = memos.toMutableList()
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            private val textViewTitle: TextView = itemView.findViewById(R.id.item_activity_delete_memo_tv_title)

            fun bind(memo: Memo) {
                textViewTitle.text = memo.title
            }
        }
    }
}