package com.deepseat.ds.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.databinding.RowMenuBinding
import com.deepseat.ds.viewholder.MenuViewHolder
import com.deepseat.ds.vo.MenuVO

class MenuAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: ArrayList<MenuVO> = ArrayList()
    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MenuViewHolder(
            RowMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MenuViewHolder

        holder.bind(data[position])
        holder.onItemClickListener = this.onItemClickListener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return MenuViewHolder.VIEW_TYPE
    }
}