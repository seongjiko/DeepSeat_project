package com.deepseat.ds.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.databinding.RowBsCommunityItemBinding

class CommunityItemViewHolder(private val binding: RowBsCommunityItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val VIEW_TYPE = R.layout.row_bs_community_item
    }

    private var expanded: Boolean = false
    var onItemClickListener: ((roomID: Int, expanded: Boolean) -> Unit)? = null

    fun bind(title: String, roomID: Int) {
        binding.txtBsCommunityRoom.text = title
        binding.btnCommunityItem.setOnClickListener {
            this.onItemClickListener?.let {
                this.expanded = !this.expanded
                it(roomID, this.expanded)
            }
        }
    }
}