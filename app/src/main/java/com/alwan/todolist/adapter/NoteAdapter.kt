package com.alwan.todolist.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alwan.todolist.R
import com.alwan.todolist.databinding.ItemNoteBinding
import com.alwan.todolist.data.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private val mData = ArrayList<Note>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(noteItem: Note) {
            binding.tvTitleNote.text = noteItem.title
            binding.tvDescriptionNote.text = noteItem.description
            binding.cbStatusNote.isChecked = noteItem.isChecked
            if(noteItem.isChecked){
                binding.itemNote.setBackgroundResource(R.drawable.bg_note_checked)
            }else{
                binding.itemNote.setBackgroundResource(R.drawable.bg_note)
            }

            binding.cbStatusNote.setOnClickListener{
                onItemClickCallback?.onCheckboxClicked(noteItem)
            }
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(noteItem)
            }
        }
    }

    fun setData(items: ArrayList<Note>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Note)
        fun onCheckboxClicked(data: Note)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val mView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_note, viewGroup, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    top = spaceHeight * 2
                }
                left = spaceHeight * 2
                right = spaceHeight * 2
                bottom = spaceHeight
            }
        }
    }
}