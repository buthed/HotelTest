package com.tematihonov.hoteltest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.hoteltest.data.models.responseHotel.Hotel
import com.tematihonov.hoteltest.domain.usecase.NetworkUseCases
import kotlinx.coroutines.launch

class HotelViewModel(
    private val networkUseCases: NetworkUseCases
): ViewModel() {

    val hotelModel = MutableLiveData<Hotel>()
    val isLoading = MutableLiveData<Boolean>()

//    private val _hotel = MutableLiveData<Resource<Hotel>>()
//    val hotel: LiveData<Resource<Hotel>> = _hotel


    var arb = mutableListOf<Int>(1,2,3)

    init {
        loadHotel()
    }

    fun loadHotel() {
        viewModelScope.launch {
//            _hotel.postValue(Resource.Loading())
//            _hotel.postValue(networkUseCases.getHotelUseCase.invoke())

            isLoading.postValue(true)
            val result = networkUseCases.getHotelUseCase.invoke()
            if (result.isSuccessful) {
                hotelModel.postValue(result.body())
                isLoading.postValue(false)
            }
        }
    }


//    data class UiState(
//        val isLoading: Boolean = false,
//        val error: Error? = null,
//        val item: Hotel? = null
//    ) {
//        sealed class Error {
//            object NetworkError : Error()
//        }
//    }

}