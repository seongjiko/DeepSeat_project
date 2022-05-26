package com.deepseat.ds.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.databinding.RowBsCommunityHeaderBinding
import com.deepseat.ds.databinding.RowBsCommunityItemBinding
import com.deepseat.ds.databinding.RowBsCommunitySubBinding
import com.deepseat.ds.model.Room
import com.deepseat.ds.model.Seat
import com.deepseat.ds.viewholder.CommunityHeaderViewHolder
import com.deepseat.ds.viewholder.CommunityItemViewHolder
import com.deepseat.ds.viewholder.CommunitySubViewHolder

class CommunityAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var rooms: ArrayList<Room> = ArrayList()
    var seats: HashMap<Int, ArrayList<Seat>> = HashMap()
    private var _data: ArrayList<Any> = ArrayList()
    private var expanded: Int = -1
    private var expandedID: Int = -1

    @SuppressLint("NotifyDataSetChanged")
    fun setData(rooms: ArrayList<Room>, seats: HashMap<Int, ArrayList<Seat>>) {
        this._data.clear()
        this.rooms.clear()
        this.seats.clear()

        this.rooms.addAll(rooms)
        this.seats.putAll(seats)

        this.rooms.forEach {
            _data.add(it)
        }

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(rooms: Array<Room>) {
        this._data.clear()
        this.rooms.clear()

        this.rooms.addAll(rooms)

        this.rooms.forEach {
            _data.add(it)
        }

        notifyDataSetChanged()
    }

    fun setData(seats: HashMap<Int, ArrayList<Seat>>) {
        this.seats.clear()
        this.seats.putAll(seats)
    }

    fun clearSeats() {
        this.seats.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(roomID: Int, seats: ArrayList<Seat>) {
        this.seats[roomID] = seats
        notifyDataSetChanged()
    }

    var onCommunitySelectListener: ((roomID: Int, seatID: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CommunityItemViewHolder.VIEW_TYPE -> CommunityItemViewHolder(
                RowBsCommunityItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            CommunitySubViewHolder.VIEW_TYPE -> CommunitySubViewHolder(
                RowBsCommunitySubBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            else -> CommunityHeaderViewHolder(
                RowBsCommunityHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommunityHeaderViewHolder -> {
                holder.bind(context.getString(R.string.community_title))
            }

            is CommunityItemViewHolder -> {
                val data = this._data[position - 1] as Room
                holder.bind(data.roomName, data.roomID)
                holder.onItemClickListener = { roomID, expanded ->
                    if (expandedID != roomID) {
                        handleExpand(position - 1, roomID)
                        this.expanded = position - 1
                        this.expandedID = roomID
                    }
                    else {
                            this.expandedID = -1
                            this.expanded = -1

                        handleHide(position - 1, roomID)
                    }

                }
            }

            is CommunitySubViewHolder -> {
                val data = this._data[position - 1] as Seat
                holder.bind(data.seatID.toString(), data.seatID)
                holder.onClickListener = { roomID ->
                    this.onCommunitySelectListener?.let {
                        it(data.roomID, roomID)
                    }
                }
            }
        }
    }

    private fun handleExpand(position: Int, roomID: Int) {
        if (expanded != -1 && expandedID != -1) handleHide(expanded, expandedID)

        this.seats[roomID]?.let {
            this._data.addAll(position + 1, it)
            notifyItemRangeInserted(position + 2, it.size)
        }
    }

    private fun handleHide(position: Int, roomID: Int) {
        this._data.removeIf { it is Seat }
        notifyItemRangeRemoved(position + 2, this.seats[roomID]?.size ?: 0)
    }

    override fun getItemCount(): Int {
        return _data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        return if (position == 0) CommunityHeaderViewHolder.VIEW_TYPE
        else if (_data[position - 1] is Room) CommunityItemViewHolder.VIEW_TYPE
        else CommunitySubViewHolder.VIEW_TYPE
    }
}