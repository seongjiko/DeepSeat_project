package com.deepseat.ds.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.databinding.RowCmDetailCommentBinding
import com.deepseat.ds.viewholder.CommentViewHolder
import com.deepseat.ds.vo.CommentVO

class CommentAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: ArrayList<CommentVO> = ArrayList()

    var onItemClickListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentViewHolder(
            RowCmDetailCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as CommentViewHolder

        holder.bind(data[position])

        holder.onClickListener = {
            this.onItemClickListener?.let { it(position) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return CommentViewHolder.VIEW_TYPE
    }


}