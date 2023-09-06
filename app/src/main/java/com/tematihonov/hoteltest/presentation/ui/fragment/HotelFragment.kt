package com.tematihonov.hoteltest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.FragmentHotelBinding
import com.tematihonov.hoteltest.domain.models.responseHotel.Hotel
import com.tematihonov.hoteltest.presentation.ui.rcview.hotelimageslist.HotelImagesAdapter
import com.tematihonov.hoteltest.presentation.ui.rcview.peculiaritylist.PeculiarityListAdapter
import com.tematihonov.hoteltest.presentation.ui.util.colorStateList
import com.tematihonov.hoteltest.presentation.ui.util.priceConverter
import com.tematihonov.hoteltest.presentation.viewmodel.HotelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HotelFragment : Fragment() {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!

    private val hotelViewModel by viewModel<HotelViewModel>()
    private lateinit var adapter: PeculiarityListAdapter
    private lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkDataAndSetNewValues()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkDataAndSetNewValues() = with(binding) {
        hotelViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == false) {
                hotelViewModel.hotelModel.observe(viewLifecycleOwner) { hotel ->
                    hotelCarousel(hotel.image_urls)
                    hotelPeculiarities(hotel.about_the_hotel.peculiarities)
                    hotelName.text = hotel.name
                    hotelAddress.text = hotel.adress
                    hotelMinimalPrice.text =
                        getString(R.string.hotel_minimal_price, priceConverter(hotel.minimal_price))
                    hotelPriceForIt.text = hotel.price_for_it
                    hotelRating.text = hotel.rating.toString()
                    hotelRatingName.text = hotel.rating_name
                    hotelAboutTheHotelDescription.text = hotel.about_the_hotel.description
                    navigation(hotel)
                }
            }
        }
    }

    private fun hotelPeculiarities(peculiarities: List<String>) {
        adapter = PeculiarityListAdapter()
        adapter.peculiarities = peculiarities
        val flexLayoutManager = FlexboxLayoutManager(this.context)
        flexLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
        binding.apply {
            hotelAboutPeculiarities.adapter = adapter
            hotelAboutPeculiarities.layoutManager = flexLayoutManager
        }
    }

    private fun hotelCarousel(imageUrls: List<String>) {
        viewPager2 = binding.carousel
        val images = listOf(imageUrls[0], imageUrls[1], imageUrls[2])
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

    fun changeColor() = with(binding) {
        when (viewPager2.currentItem) {
            0 -> {
                hotelCarouselCv1.backgroundTintList = colorStateList(R.color.black, requireContext())
                hotelCarouselCv2.backgroundTintList = colorStateList(R.color.carousel_grey, requireContext())
                hotelCarouselCv3.backgroundTintList = colorStateList(R.color.carousel_grey, requireContext())
            }
            1 -> {
                hotelCarouselCv1.backgroundTintList = colorStateList(R.color.carousel_grey, requireContext())
                hotelCarouselCv2.backgroundTintList = colorStateList(R.color.black, requireContext())
                hotelCarouselCv3.backgroundTintList = colorStateList(R.color.carousel_grey, requireContext())
            }
            2 -> {
                hotelCarouselCv1.backgroundTintList = colorStateList(R.color.carousel_grey, requireContext())
                hotelCarouselCv2.backgroundTintList = colorStateList(R.color.carousel_grey, requireContext())
                hotelCarouselCv3.backgroundTintList = colorStateList(R.color.black, requireContext())
            }
        }
    }

    private fun FragmentHotelBinding.navigation(hotel: Hotel) {
        buttonHotelToChoose.setOnClickListener {
            val action = HotelFragmentDirections.actionHotelFragmentToRoomsFragment(hotel.name)
            Navigation.findNavController(this.buttonHotelToChoose).navigate(action)
        }
    }
}