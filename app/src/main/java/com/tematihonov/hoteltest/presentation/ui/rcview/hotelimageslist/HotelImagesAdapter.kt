package com.tematihonov.hoteltest.presentation.ui.rcview.hotelimageslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tematihonov.hoteltest.R

class HotelImagesAdapter(private val images: List<String>): RecyclerView.Adapter<HotelImagesAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.carousel_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelImagesAdapter.ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelImagesAdapter.ViewPagerViewHolder, position: Int) {
        val currentImage = images[position]
        holder.imageView.load(currentImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}