package com.yologger.simple_memo.presentation.screen.reorder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.model.Memo
import java.util.*

class ReorderableRecyclerViewAdapter
constructor(
    private val viewModel: ReorderViewModel
) : RecyclerView.Adapter<ReorderableRecyclerViewAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onClicked(viewHolder: ViewHolder, position: Int)
    }

    var listener: OnClickListener? = null

    var memos = mutableListOf<Memo>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.item_activity_reorder_memo_tv_title)
        val imageViewReorder: ImageView = itemView.findViewById(R.id.item_activity_reorder_iv_reorder)

        fun bind(memo: Memo) {
            textViewTitle.text = memo.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_reorder_memo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = memos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listener != null) {
            holder.imageViewReorder.setOnTouchListener { v, event ->
                listener?.onClicked(holder, position)
                return@setOnTouchListener false
            }
        }
        holder.bind(memos[position])
    }

    fun onItemMove(from: Int, to: Int) {
        Collections.swap(memos, from, to)
        notifyItemMoved(from, to)
    }

    fun setupMemos(memos: List<Memo>) {
        this.memos = memos.toMutableList()
        notifyDataSetChanged()
    }

    fun updatePositions() {
        viewModel.updateMemos(memos)
    }
}