package com.tematihonov.hoteltest.presentation.ui.rcview.roomslist

import android.content.Context
import android.content.res.ColorStateList
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.data.models.responceRooms.Room
import com.tematihonov.hoteltest.databinding.ItemRoomBinding
import com.tematihonov.hoteltest.presentation.ui.rcview.hotelimageslist.HotelImagesAdapter
import com.tematihonov.hoteltest.presentation.ui.rcview.peculiaritylist.PeculiarityListAdapter

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
        with(holder.binding) {
            roomName.text = room.name
            roomPrice.text = "${room.price} ${Html.fromHtml(" &#x20bd")}" //TODO fix?
            roomPricePer.text = room.price_per
            buttonChooseRoom.setOnClickListener {
                rListener.onClick(position)
            }
            roomCarousel(room.image_urls, holder.binding)
            roomPeculiarities(room.peculiarities, holder.binding)
            //TODO add peculiarities

        }
    }

    private fun roomPeculiarities(peculiarities: List<String>, binding: ItemRoomBinding) {
        val adapter = PeculiarityListAdapter()
        val layoutManager = GridLayoutManager(this.context, 2) //TODO recheck
        binding.roomPeculiarities.layoutManager = layoutManager
        binding.roomPeculiarities.adapter = adapter
        adapter.peculiarities = peculiarities
    }


    fun roomCarousel(imageUrls: List<String>, binding: ItemRoomBinding) {
        var viewPager2: ViewPager2 = binding.carousel
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

    fun changeColor(binding: ItemRoomBinding, viewPager2: ViewPager2) {
        when (viewPager2.currentItem) {
            0 -> {
                binding.hotelCarouselCv1.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.black))
                binding.hotelCarouselCv2.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.carousel_grey))
                binding.hotelCarouselCv3.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.carousel_grey))
                Log.d("GGG", "Carousel 0")
            }
            1 -> {
                binding.hotelCarouselCv1.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.carousel_grey))
                binding.hotelCarouselCv2.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.black))
                binding.hotelCarouselCv3.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.carousel_grey))
                Log.d("GGG", "Carousel 1")
            }
            2 -> {
                binding.hotelCarouselCv1.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.carousel_grey))
                binding.hotelCarouselCv2.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.carousel_grey))
                binding.hotelCarouselCv3.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(
                    R.color.black))
                Log.d("GGG", "Carousel 2")
            } //TODO del deprecaited
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}