package com.deepseat.ds.viewholder

import android.util.Log
import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.GlobalData
import com.deepseat.ds.R
import com.deepseat.ds.activity.CommunityDetailActivity
import com.deepseat.ds.databinding.RowCmDetailCommentBinding
import com.deepseat.ds.vo.CommentVO

class CommentViewHolder(private val binding: RowCmDetailCommentBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

    companion object {
        const val VIEW_TYPE = R.layout.row_cm_detail_comment
    }

    private lateinit var data: CommentVO

    var onClickListener: (() -> Unit)? = null

    init {
        binding.root.setOnCreateContextMenuListener(this)
    }

    fun bind(data: CommentVO) {
        this.data = data

        binding.txtRowCmDetailNickname.text = data.nickname
        binding.txtRowCmDetailTimestamp.text = data.wrote
        binding.txtRowCmDetailContent.text = data.content
        binding.txtRowCommentLiked.text = data.liked.toString()

    }

    override fun onCreateContextMenu(
        p0: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        val activity = (p1?.context as CommunityDetailActivity)
        activity.menuInflater.inflate(R.menu.menu_context_comment, p0)

        if (data.userID != GlobalData.user?.userId) {
            p0?.removeItem(R.id.action_comment_edit)
            p0?.removeItem(R.id.action_comment_delete)
        }
    }


}