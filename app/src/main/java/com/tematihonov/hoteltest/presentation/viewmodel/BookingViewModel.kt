package com.tematihonov.hoteltest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.domain.usecase.NetworkUseCases
import kotlinx.coroutines.launch

class BookingViewModel(
    private val networkUseCases: NetworkUseCases
): ViewModel() {

    val bookingModel = MutableLiveData<Booking>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        loadHotel()
    }

    private fun loadHotel() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = networkUseCases.getBookingUseCase.invoke()
            if (result.isSuccessful) {
                bookingModel.postValue(result.body())
                isLoading.postValue(false)
            }
        }
    }
}