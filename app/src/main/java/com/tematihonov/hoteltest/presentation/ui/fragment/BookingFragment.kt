package com.tematihonov.hoteltest.presentation.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.FragmentBookingBinding
import com.tematihonov.hoteltest.presentation.ui.rcview.bookingtourists.BookingTouristsAdapter
import com.tematihonov.hoteltest.presentation.ui.util.NumberTextWatcher
import com.tematihonov.hoteltest.presentation.ui.util.priceConverter
import com.tematihonov.hoteltest.presentation.viewmodel.BookingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private val bookingViewModel by viewModel<BookingViewModel>()
    private lateinit var adapter: BookingTouristsAdapter

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

        adapter = BookingTouristsAdapter(bookingViewModel)
        adapter.setOnItemClickListener(object : BookingTouristsAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                bookingViewModel.hideExpandTouristInputs(position)
                Log.d("GGG", "list ${bookingViewModel.touristsList.value}")
            }
        })
        val layoutManager = LinearLayoutManager(this.context)
        binding.bookingTouristList.layoutManager = layoutManager
        binding.bookingTouristList.adapter = adapter

        bookingViewModel.touristsList.observe(viewLifecycleOwner) {
            adapter.tourists = it
        }
        binding.bookingAddTouristButton.setOnClickListener {
            bookingViewModel.addNewTourist()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkDataAndSetNewValues() = with(binding) {
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

                    numberInput()
                }
            }
        }
    }

    private fun FragmentBookingBinding.numberInput() {
        bookingInputPhone.addTextChangedListener(NumberTextWatcher())
    }

    private fun navigation() = with(binding) {
        appBar.setOnClickListener {
            findNavController().popBackStack()
        }
        bookingPayButton.setOnClickListener {

            if (checkInputs() && bookingViewModel.checkTouristsData()) {
                findNavController().navigate(R.id.action_bookingFragment2_to_orderFragment)
            } else {
                Toast.makeText(requireContext(), getString(R.string.booking_error_toast), Toast.LENGTH_SHORT).show()
                Log.d("GGG", "tourist: ${bookingViewModel.touristsList.value?.get(0)}")
            }
        }
    }

    private fun checkInputs(): Boolean {
        checkMailInput()
        checkPhoneInput()
        return checkMailInput() && checkPhoneInput()
    }

    private fun checkPhoneInput(): Boolean = with(binding) {
        if(bookingInputPhone.text!!.length == 18) {
            Log.d("GGG", "Phone GOOD")
            bookingInputPhone.backgroundTintList = colorStateList(R.color.edit_text_bg)
            return true
        } else {
            bookingInputPhone.backgroundTintList = colorStateList(R.color.error)
            Log.d("GGG", "Phone BAD")
            return false
        }
    }

    private fun checkMailInput(): Boolean = with(binding)  {
        if(!bookingInputMail.text.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(bookingInputMail.text.toString()).matches()) {
            Log.d("GGG", "EMAIL GOOD")
            bookingInputMail.backgroundTintList = colorStateList(R.color.edit_text_bg)
            return true
        } else {
            bookingInputMail.backgroundTintList = colorStateList(R.color.error)
            Log.d("GGG", "EMAIL BAD") //TODO del logs
            return false
        }
    }

    private fun colorStateList(color: Int) =
        ColorStateList.valueOf(ContextCompat.getColor(requireContext(), color))
}