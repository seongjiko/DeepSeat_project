package com.deepseat.ds.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.databinding.RowBsCommunitySubBinding

class CommunitySubViewHolder(private val binding: RowBsCommunitySubBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        const val VIEW_TYPE = R.layout.row_bs_community_sub
    }

    var onClickListener: ((seatID: Int) -> Unit)? = null

    fun bind(seatName: String, seatID: Int) {
        binding.txtBsCommunitySeat.text = seatName
        binding.btnCommunitySub.setOnClickListener {
            this.onClickListener?.let {
                it(seatID)
            }
        }
    }
}