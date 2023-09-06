package com.tematihonov.hoteltest.presentation.ui.rcview.roomslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.ItemRoomBinding
import com.tematihonov.hoteltest.domain.models.responceRooms.Room
import com.tematihonov.hoteltest.presentation.ui.rcview.hotelimageslist.HotelImagesAdapter
import com.tematihonov.hoteltest.presentation.ui.rcview.peculiaritylist.PeculiarityListAdapter
import com.tematihonov.hoteltest.presentation.ui.util.colorStateList
import com.tematihonov.hoteltest.presentation.ui.util.priceConverter

class RoomsListAdapter() : RecyclerView.Adapter<RoomsListAdapter.RoomsListViewHolder>() {

    private lateinit var rListener: onItemClickListener
    private lateinit var context: Context

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

        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomBinding.inflate(inflater, parent, false)
        return RoomsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsListAdapter.RoomsListViewHolder, position: Int) {

        val room = rooms[position]
        holder.binding.apply {
            roomName.text = room.name
            roomPrice.text = priceConverter(room.price)
            roomPricePer.text = room.price_per
            buttonChooseRoom.setOnClickListener {
                rListener.onClick(position)
            }
            roomCarousel(room.image_urls, holder.binding)
            roomPeculiarities(room.peculiarities, holder.binding)
        }
    }

    private fun roomPeculiarities(peculiarities: List<String>, binding: ItemRoomBinding) {
        val adapter = PeculiarityListAdapter()
        adapter.peculiarities = peculiarities
        val flexLayoutManager = FlexboxLayoutManager(this.context)
        flexLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent =  JustifyContent.FLEX_START
        }
        binding.roomPeculiarities.adapter = adapter
        binding.roomPeculiarities.layoutManager = flexLayoutManager
    }


    private fun roomCarousel(imageUrls: List<String>, binding: ItemRoomBinding) {
        val viewPager2: ViewPager2 = binding.carousel
        val images = listOf(imageUrls[0], imageUrls[1], imageUrls[2])
        val adapter = HotelImagesAdapter(images)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                changeColor(binding, viewPager2)
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeColor(binding, viewPager2)
            }
        })
    }

    fun changeColor(binding: ItemRoomBinding, viewPager2: ViewPager2) = with(binding) {
        when (viewPager2.currentItem) {
            0 -> {
                hotelCarouselCv1.backgroundTintList = colorStateList(R.color.black, context)
                hotelCarouselCv2.backgroundTintList = colorStateList(R.color.carousel_grey, context)
                hotelCarouselCv3.backgroundTintList = colorStateList(R.color.carousel_grey, context)
            }
            1 -> {
                hotelCarouselCv1.backgroundTintList = colorStateList(R.color.carousel_grey, context)
                hotelCarouselCv2.backgroundTintList = colorStateList(R.color.black, context)
                hotelCarouselCv3.backgroundTintList = colorStateList(R.color.carousel_grey, context)
            }
            2 -> {
                hotelCarouselCv1.backgroundTintList = colorStateList(R.color.carousel_grey, context)
                hotelCarouselCv2.backgroundTintList = colorStateList(R.color.carousel_grey, context)
                hotelCarouselCv3.backgroundTintList = colorStateList(R.color.black, context)
            }
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}