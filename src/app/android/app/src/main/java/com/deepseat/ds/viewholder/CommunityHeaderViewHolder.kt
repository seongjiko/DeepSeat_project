package com.deepseat.ds.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.databinding.RowBsCommunityHeaderBinding

class CommunityHeaderViewHolder(private val binding: RowBsCommunityHeaderBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val VIEW_TYPE = R.layout.row_bs_community_header
    }

    fun bind(title: String) {
        binding.txtBsCommunityHeader.text = title
    }
}