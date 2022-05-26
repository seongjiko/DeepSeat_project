package com.deepseat.ds.viewholder

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.databinding.RowCommunityListBinding
import com.deepseat.ds.vo.CommunityListVO

class CommunityListViewHolder(private val binding: RowCommunityListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        val VIEW_TYPE = R.layout.row_community_list

        enum class ClickedItem {
            CARD, LIKED, COMMENTS, MORE, ROOM, SEAT
        }
    }

    var onClickListener: ((clickedItem: ClickedItem) -> Unit)? = null
    var iLiked: Boolean = false

    fun bind(data: CommunityListVO) {

        binding.txtCmlstContent.text = data.content
        binding.txtCmlstWritten.text = data.wrote
        binding.txtCmlstWriter.text = data.nickname
        binding.chipCmlstRoom.text = data.roomName
        binding.chipCmlstSeat.text = data.seatName

        iLiked = data.iLiked

        // Mark liked icon if I liked
        toggleLiked()

        // Show comments info
        if (data.comments > 0) {
            binding.imgCmlstComments.visibility = View.VISIBLE
            binding.txtCmlstComments.visibility = View.VISIBLE
            binding.txtCmlstComments.text = data.comments.toString()
        } else {
            binding.imgCmlstComments.visibility = View.GONE
            binding.txtCmlstComments.visibility = View.GONE
        }

        // Show liked info
        if (data.liked > 0) {
            binding.imgCmlstLiked.visibility = View.VISIBLE
            binding.imgCmlstLiked.visibility = View.VISIBLE
            binding.txtCmlstLiked.text = data.liked.toString()
        } else {
            binding.imgCmlstLiked.visibility = View.GONE
            binding.txtCmlstLiked.visibility = View.GONE
        }

        // If no likes and comments, hide info view
        if (data.liked <= 0 && data.comments <= 0) {
            binding.lnrCmlstInfo.visibility = View.GONE
        }

        // Card ClickListener
        binding.cardCmlst.setOnClickListener {
            this.onClickListener?.let { it(ClickedItem.CARD) }
        }

        // Liked ClickListener
        binding.btnCmlstLike.setOnClickListener {
            toggleLiked()
            this.onClickListener?.let {
                it(ClickedItem.LIKED)
            }
        }

        // Comments ClickListener
        binding.btnCmlstComment.setOnClickListener {
            this.onClickListener?.let { it(ClickedItem.COMMENTS) }
        }

        // Room Chip ClickListener
        binding.chipCmlstRoom.setOnClickListener {
            this.onClickListener?.let { it(ClickedItem.ROOM) }
        }

        // Seat Chip ClickListener
        binding.chipCmlstSeat.setOnClickListener {
            this.onClickListener?.let { it(ClickedItem.SEAT) }
        }
    }

    private fun toggleLiked() {
        // Mark liked icon if I liked
        if (iLiked) {
            binding.imgCmlstLike.setImageResource(R.drawable.ic_like_filled)
            binding.imgCmlstLike.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.tint_heart))
        } else {
            binding.imgCmlstLike.setImageResource(R.drawable.ic_like_outlined)
            binding.imgCmlstLike.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, R.color.text_primary))
        }
        iLiked = !iLiked
    }

}