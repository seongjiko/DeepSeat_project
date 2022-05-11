package com.deepseat.ds.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.databinding.RowMenuBinding
import com.deepseat.ds.vo.MenuVO

class MenuViewHolder(private val binding: RowMenuBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        val VIEW_TYPE = R.layout.row_menu
    }

    var onItemClickListener: ((String) -> Unit)? = null

    fun bind(data: MenuVO) {
        binding.txtRowMenuTitle.text = data.title
        binding.imgRowMenuIcon.setImageResource(data.icon)
        binding.imgRowMenuDisclosure.visibility = if (data.accessory) View.VISIBLE else View.GONE
        binding.txtRowMenuDesc.visibility = View.GONE

        data.desc?.let {
            binding.txtRowMenuDesc.visibility = View.VISIBLE
            binding.txtRowMenuDesc.text = it
        }

        binding.layoutRowMenu.setOnClickListener {
            onItemClickListener?.let {
                it(data.id)
            }
        }
    }
}