package com.tematihonov.hoteltest.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.hoteltest.domain.usecase.NetworkUseCases
import kotlinx.coroutines.launch

class HotelViewModel(
    private val networkUseCases: NetworkUseCases
): ViewModel() {

    var arb = mutableListOf<Int>(1,2,3)

    fun initHotel() {
        viewModelScope.launch {
            var hotel = networkUseCases.getHotelUseCase.invoke()
            Log.d("GGG", "${hotel.adress}")
        }
        Log.d("GGG", "123")
    }

}