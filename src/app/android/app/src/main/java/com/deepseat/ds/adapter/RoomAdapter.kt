package com.deepseat.ds.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.databinding.RowBsSeatsItemBinding
import com.deepseat.ds.model.Room
import com.deepseat.ds.viewholder.RoomViewHolder

class RoomAdapter(private val context: Context) : RecyclerView.Adapter<RoomViewHolder>() {

    var data: ArrayList<Room> = arrayListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            this.data.clear()
            this.data.addAll(value)
            notifyDataSetChanged()
        }

    var onItemClickListener: ((room: Room, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            RowBsSeatsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(data[position])
        holder.onItemClickListener = { room ->
            this.onItemClickListener?.let { it(room, position) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}