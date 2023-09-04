package com.tematihonov.hoteltest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.FragmentBookingBinding
import com.tematihonov.hoteltest.presentation.ui.util.priceConverter
import com.tematihonov.hoteltest.presentation.viewmodel.BookingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private val bookingViewModel by viewModel<BookingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkDataAndSetNewValues()
        navigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkDataAndSetNewValues()  = with(binding) {
        bookingViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == false) {
                bookingViewModel.bookingModel.observe(viewLifecycleOwner) { booking ->
                    hotelName.text = booking.hotel_name
                    hotelAddress.text = booking.hotel_adress
                    hotelRating.text = booking.horating.toString()
                    hotelRatingName.text = booking.rating_name
                    bookingHotel.text = booking.hotel_name
                    bookingDeparture.text = booking.departure
                    bookingArrivalCountry.text = booking.arrival_country
                    bookingDates.text = getString(R.string.booking_dates_set_text, booking.tour_date_start, booking.tour_date_stop)
                    bookingNumberOfNights.text = getString(R.string.booking_number_of_nights_set_text,
                        booking.number_of_nights,
                        when (booking.number_of_nights) {
                            1 -> " ночь"
                            2-4 -> " ночи"
                            else -> " ночей"})

                    bookingRoom.text = booking.room
                    bookingNutrition.text = booking.nutrition
                    bookingTourPrice.text = priceConverter(booking.tour_price)
                    bookingFuelCharge.text = priceConverter(booking.fuel_charge)
                    bookingServiceCharge.text = priceConverter(booking.service_charge)

                    val totalToPay = listOf(booking.tour_price,booking.fuel_charge,booking.service_charge)
                    bookingToPay.text = priceConverter(totalToPay.sum())
                    bookingPayButton.text = priceConverter(totalToPay.sum())
                }
            }
        }
    }

    private fun navigation() = with(binding) {
        appBar.setOnClickListener {
            findNavController().popBackStack()
        }
        bookingPayButton.setOnClickListener {
            findNavController().navigate(R.id.action_bookingFragment2_to_orderFragment)
        } //TODO add check
    }
}