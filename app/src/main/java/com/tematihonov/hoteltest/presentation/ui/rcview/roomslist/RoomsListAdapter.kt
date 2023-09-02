package com.tematihonov.hoteltest.presentation.ui.rcview.roomslist

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tematihonov.hoteltest.data.models.responceRooms.Room
import com.tematihonov.hoteltest.databinding.ItemRoomBinding

class RoomsListAdapter() : RecyclerView.Adapter<RoomsListAdapter.RoomsListViewHolder>() {

    private lateinit var rListener: onItemClickListener

    interface onItemClickListener {

        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        rListener = listener
    }

    var rooms: List<Room> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    inner class RoomsListViewHolder(val binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RoomsListAdapter.RoomsListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomBinding.inflate(inflater, parent, false)
        return RoomsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsListAdapter.RoomsListViewHolder, position: Int) {
        val room = rooms[position]
        with(holder.binding) {
            roomName.text = room.name
            roomPrice.text = "${room.price} ${Html.fromHtml(" &#x20bd")}" //TODO fix?
            roomPricePer.text = room.price_per
            buttonChooseRoom.setOnClickListener {
                rListener.onClick(position)
            }
            //TODO add carousel
            //TODO add peculiarities
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}