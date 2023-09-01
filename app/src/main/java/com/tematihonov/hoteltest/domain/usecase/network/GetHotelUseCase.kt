package com.tematihonov.hoteltest.domain.usecase.network

import com.tematihonov.hoteltest.data.models.responseHotel.Hotel
import com.tematihonov.hoteltest.domain.repository.NetworkRepository

class GetHotelUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(): Hotel {
        return networkRepository.getHotel()
    }
}