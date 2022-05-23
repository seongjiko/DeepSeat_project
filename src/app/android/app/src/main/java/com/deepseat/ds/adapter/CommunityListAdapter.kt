package com.deepseat.ds.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.databinding.RowCommunityListBinding
import com.deepseat.ds.viewholder.CommunityListViewHolder
import com.deepseat.ds.vo.CommunityListVO
import com.deepseat.ds.viewholder.CommunityListViewHolder.Companion.ClickedItem

class CommunityListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: ArrayList<CommunityListVO> = ArrayList()

    var onItemClickListener: ((clickedItem: ClickedItem, communityListVO: CommunityListVO) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommunityListViewHolder(
            RowCommunityListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as CommunityListViewHolder

        holder.bind(data[position])

        holder.onClickListener = { clickedItem ->
            this.onItemClickListener?.let { it(clickedItem, data[position]) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return CommunityListViewHolder.VIEW_TYPE
    }
}