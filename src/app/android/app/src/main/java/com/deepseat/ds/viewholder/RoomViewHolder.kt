package com.deepseat.ds.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.databinding.RowBsSeatsItemBinding
import com.deepseat.ds.model.Room

class RoomViewHolder(val binding: RowBsSeatsItemBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val VIEW_TYPE = R.layout.row_bs_seats_item
    }

    var onItemClickListener: ((room: Room) -> Unit)? = null

    fun bind(data: Room) {

        binding.txtRowSeatsRoom.text = data.roomName

        binding.layoutRowSeats.setOnClickListener {
            onItemClickListener?.let { it(data) }
        }

    }
}