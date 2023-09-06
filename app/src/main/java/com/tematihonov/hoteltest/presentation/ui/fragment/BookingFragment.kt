package com.tematihonov.hoteltest.presentation.ui.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.FragmentBookingBinding
import com.tematihonov.hoteltest.presentation.ui.rcview.bookingtourists.BookingTouristsAdapter
import com.tematihonov.hoteltest.presentation.ui.util.NumberTextWatcher
import com.tematihonov.hoteltest.presentation.ui.util.colorStateList
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
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkDataAndSetNewValues()
        navigation()
        touristsRecycler()
        addTourist()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addTourist() {
        binding.bookingAddTouristButton.setOnClickListener {
            bookingViewModel.addNewTourist()
            adapter.tourists = bookingViewModel.touristsList.value!!
        }
    }

    private fun touristsRecycler() {
        adapter = BookingTouristsAdapter(bookingViewModel)
        adapter.setOnItemClickListener(object : BookingTouristsAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                bookingViewModel.hideExpandTouristInputs(position)
            }
        })

        val layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            bookingTouristList.layoutManager = layoutManager
            bookingTouristList.adapter = adapter
        }
        bookingViewModel.touristsList.observe(viewLifecycleOwner) {
            adapter.tourists = it
        }
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
                    bookingDates.text = getString(
                        R.string.booking_dates_set_text,
                        booking.tour_date_start,
                        booking.tour_date_stop
                    )
                    bookingNumberOfNights.text = getString(
                        R.string.booking_number_of_nights_set_text,
                        booking.number_of_nights,
                        when (booking.number_of_nights) {
                            1 -> " ночь"
                            2 - 4 -> " ночи"
                            else -> " ночей"
                        }
                    )
                    bookingRoom.text = booking.room
                    bookingNutrition.text = booking.nutrition
                    bookingTourPrice.text = priceConverter(booking.tour_price)
                    bookingFuelCharge.text = priceConverter(booking.fuel_charge)
                    bookingServiceCharge.text = priceConverter(booking.service_charge)
                    val totalToPay =
                        listOf(booking.tour_price, booking.fuel_charge, booking.service_charge)
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
        val incorrectDataToast = Toast.makeText(
            requireContext(),
            getString(R.string.booking_error_toast),
            Toast.LENGTH_SHORT
        )
        bookingPayButton.setOnClickListener {
            if (checkInputs()) { findNavController().navigate(R.id.action_bookingFragment2_to_orderFragment)
            } else { incorrectDataToast.show() }
        }
    }

    private fun checkInputs(): Boolean {
        if (!bookingViewModel.checkTouristsData()) {
            adapter.tourists = bookingViewModel.touristsList.value!!
        }
        checkMailInput()
        checkPhoneInput()
        return checkMailInput() && checkPhoneInput() && bookingViewModel.checkTouristsData()
    }

    private fun checkPhoneInput(): Boolean = with(binding) {
        bookingInputPhone.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && bookingInputPhone.backgroundTintList == colorStateList(R.color.error, requireContext())) {
                if (bookingInputPhone.text!!.length == 18)
                    bookingInputPhone.backgroundTintList = colorStateList(R.color.edit_text_bg, requireContext())
            }
        }
        if (bookingInputPhone.text!!.length == 18) {
            bookingInputPhone.backgroundTintList = colorStateList(R.color.edit_text_bg, requireContext())
            return true
        } else {
            bookingInputPhone.backgroundTintList = colorStateList(R.color.error, requireContext())
            return false
        }
    }

    private fun checkMailInput(): Boolean = with(binding) {
        bookingInputMail.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && bookingInputMail.backgroundTintList == colorStateList(R.color.error, requireContext())) {
                if (!bookingInputMail.text.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(
                        bookingInputMail.text.toString()
                    ).matches()
                ) bookingInputMail.backgroundTintList = colorStateList(R.color.edit_text_bg, requireContext())
            }
        }

        if (!bookingInputMail.text.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(
                bookingInputMail.text.toString()
            ).matches()
        ) {
            bookingInputMail.backgroundTintList = colorStateList(R.color.edit_text_bg, requireContext())
            return true
        } else {
            bookingInputMail.backgroundTintList = colorStateList(R.color.error, requireContext())
            return false
        }
    }
}