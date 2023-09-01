package com.tematihonov.hoteltest.presentation.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.FragmentHotelBinding
import com.tematihonov.hoteltest.presentation.ui.rcview.hotelimageslist.HotelImagesAdapter

class HotelFragment : Fragment() {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonHotelToChoose.setOnClickListener {
            val action = HotelFragmentDirections.actionHotelFragmentToRoomsFragment("Some Hotel") //TODO add hotel name
            Navigation.findNavController(view).navigate(action)
        }

        hotelCarousel()
    }

    private fun hotelCarousel() {
        viewPager2 = binding.carousel
        val images =
            listOf(R.drawable.carusel_test1, R.drawable.carusel_test2, R.drawable.carusel_test3)
        val adapter = HotelImagesAdapter(images)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
                changeColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeColor()
            }
        })
    }

    fun changeColor() {
        when (viewPager2.currentItem) {
            0 -> {
                binding.hotelCarouselCv1.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.black))
                binding.hotelCarouselCv2.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.carousel_grey))
                binding.hotelCarouselCv3.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.carousel_grey))
            }
            1 -> {
                binding.hotelCarouselCv1.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.carousel_grey))
                binding.hotelCarouselCv2.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.black))
                binding.hotelCarouselCv3.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.carousel_grey))
            }
            2 -> {
                binding.hotelCarouselCv1.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.carousel_grey))
                binding.hotelCarouselCv2.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.carousel_grey))
                binding.hotelCarouselCv3.backgroundTintList = ColorStateList.valueOf(this.resources.getColor(R.color.black))
            } //TODO del depricated
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    
}