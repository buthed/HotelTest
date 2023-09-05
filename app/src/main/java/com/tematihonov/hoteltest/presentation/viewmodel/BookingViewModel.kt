package com.tematihonov.hoteltest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.hoteltest.data.models.Tourist
import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.domain.usecase.NetworkUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookingViewModel(
    private val networkUseCases: NetworkUseCases
): ViewModel() {

    val bookingModel = MutableLiveData<Booking>()
    val isLoading = MutableLiveData<Boolean>()
    val isTourisCheck = MutableLiveData<Boolean>()

    val touristsList = MutableLiveData(arrayListOf(Tourist("Первый турист", true)))

    init {
        loadHotel()
    }

    fun addNewTourist() {
        val tourists = touristsList.value
        if (tourists != null) {
            when (tourists.size) {
                1 -> {
                    tourists.add(Tourist("Второй турист", false))
                    touristsList.postValue(tourists)
                }
                2 -> {
                    tourists.add(Tourist("Третий турист", false))
                    touristsList.postValue(tourists)
                }
                else -> {}
            }
        }
    }

    fun updateTourist(position: Int, parameter: Int, newValue: String) {
        viewModelScope.launch {
            val tourists = touristsList.value
            val tourist = tourists!![position]

            when(parameter) {
                1 -> { tourist.firstName = newValue }
                2 -> { tourist.secondName = newValue }
                3 -> { tourist.dateOfBirth = newValue }
                4 -> { tourist.citizen = newValue }
                5 -> { tourist.passportNumber = newValue }
                6 -> { tourist.passportValidation = newValue }
            }
            tourists[position] = tourist
            touristsList.postValue(tourists)
        }
    }

    fun checkTouristsData(): Boolean {
        viewModelScope.launch {
            isTourisCheck.value = true
            delay(100)
            isTourisCheck.postValue(false)
        }
        touristsList.value!!.forEach {
            if (
                it.firstName.isEmpty() ||
                it.secondName.isEmpty() ||
                it.dateOfBirth.isEmpty() ||
                it.citizen.isEmpty() ||
                it.passportNumber.isEmpty() ||
                it.passportValidation.isEmpty()
            ) return false
        }
        return true
    }

    fun hideExpandTouristInputs(position: Int) {
        viewModelScope.launch {
            val tourists: ArrayList<Tourist> = touristsList.value!!

            when (tourists[position].expand) {
                true -> { tourists[position].expand = false }
                false -> { tourists[position].expand = true }
            }
            touristsList.postValue(tourists)
        }
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