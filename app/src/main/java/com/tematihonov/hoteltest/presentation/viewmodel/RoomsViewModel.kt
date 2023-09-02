package com.tematihonov.hoteltest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.hoteltest.data.models.responceRooms.Rooms
import com.tematihonov.hoteltest.domain.usecase.NetworkUseCases
import kotlinx.coroutines.launch

class RoomsViewModel(
    private val networkUseCases: NetworkUseCases
): ViewModel() {

    val roomsModel = MutableLiveData<Rooms>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        loadRooms()
    }

    private fun loadRooms() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = networkUseCases.getRoomsUseCase.invoke()
            if (result.isSuccessful) {
                roomsModel.postValue(result.body())
                isLoading.postValue(false)
            }
        }
    }
}