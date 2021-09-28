package com.yologger.simple_memo.presentation.screen.delete

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yologger.simple_memo.R
import com.yologger.simple_memo.presentation.model.Memo
import com.yologger.simple_memo.presentation.model.SelectableMemo

class SelectableRecyclerViewAdapter
constructor(
        private var viewModel: DeleteViewModel,
        private var memos: MutableList<SelectableMemo> = mutableListOf()
) : RecyclerView.Adapter<SelectableRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.item_activity_delete_memo_tv_title)
        private val imageViewChecked: ImageView = itemView.findViewById(R.id.item_activity_delete_memo_iv_check)

        fun bind(selectableMemo: SelectableMemo) {
            if(selectableMemo.isChecked) {
                imageViewChecked.visibility = View.VISIBLE
                // itemView.setBackgroundColor(Color.rgb(200, 200, 200))
            } else {
                imageViewChecked.visibility = View.INVISIBLE
                // itemView.setBackgroundColor(Color.rgb(255, 255, 255))
            }
            textViewTitle.text = selectableMemo.title

            itemView.setOnClickListener {
                selectableMemo.isChecked = !selectableMemo.isChecked
                if(selectableMemo.isChecked) {
                    // itemView.setBackgroundColor(Color.rgb(200, 200, 200))
                    imageViewChecked.visibility = View.VISIBLE
                } else {
                    // itemView.setBackgroundColor(Color.rgb(255, 255, 255))
                    imageViewChecked.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun getItemCount(): Int =  memos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_delete_memo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).bind(memos[position])
    }

    fun updateMemos(memos: List<Memo>) {
        val selectableMemo = memos.map { SelectableMemo(id = it.id, title = it.title, content = it.content, position = it.position) }.toMutableList()
        this.memos = selectableMemo
        notifyDataSetChanged()
    }

    fun deleteMemos() {
        var targetSelectableMemo = mutableListOf<SelectableMemo>()
        for (memo in memos) {
            if (memo.isChecked) { targetSelectableMemo.add(memo) }
        }
        val targetMemos = targetSelectableMemo.map { Memo(id = it.id, title = it.title, content = it.content, position = it.position) }
        viewModel.deleteMemos(targetMemos)
    }
}