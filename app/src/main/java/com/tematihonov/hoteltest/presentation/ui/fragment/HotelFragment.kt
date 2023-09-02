package com.tematihonov.hoteltest.presentation.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.FragmentHotelBinding
import com.tematihonov.hoteltest.presentation.ui.rcview.hotelimageslist.HotelImagesAdapter
import com.tematihonov.hoteltest.presentation.viewmodel.HotelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HotelFragment : Fragment() {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!

    private val hotelViewModel by viewModel<HotelViewModel>()
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

        checkDataAndSetNewValues()
        hotelCarousel()

        binding.buttonHotelToChoose.setOnClickListener {
            val action = HotelFragmentDirections.actionHotelFragmentToRoomsFragment(hotelViewModel.hotelModel.value?.name
                ?: "") //TODO add to fun?
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun checkDataAndSetNewValues() = with(binding) {
        hotelViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == false) {
                hotelViewModel.hotelModel.observe(viewLifecycleOwner) { hotel ->
                    hotelName.text = hotel.name
                    hotelAddress.text = hotel.adress
                    hotelMinimalPrice.text =
                        "от ${hotel.minimal_price} ${Html.fromHtml(" &#x20bd")}" //TODO fix?
                    hotelPriceForIt.text = hotel.price_for_it
                    hotelRating.text = hotel.rating.toString()
                    hotelRatingName.text = hotel.rating_name
                    hotelAboutTheHotelDescription.text = hotel.about_the_hotel.description
                    //TODO add peculiarities
                    //TODO add carousel images
                    Log.d("GGG", "Success ${hotel.name}")
                }
            }
        }
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
            } //TODO del deprecaited
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}