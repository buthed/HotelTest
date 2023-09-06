package com.tematihonov.hoteltest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.hoteltest.domain.models.responseHotel.Hotel
import com.tematihonov.hoteltest.domain.usecase.NetworkUseCases
import kotlinx.coroutines.launch

class HotelViewModel(
    private val networkUseCases: NetworkUseCases
): ViewModel() {

    val hotelModel = MutableLiveData<Hotel>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        loadHotel()
    }

    private fun loadHotel() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = networkUseCases.getHotelUseCase.invoke()
            if (result.isSuccessful) {
                hotelModel.postValue(result.body())
                isLoading.postValue(false)
            }
        }
    }
}