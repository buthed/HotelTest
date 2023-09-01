package com.tematihonov.hoteltest.domain.usecase.network

import com.tematihonov.hoteltest.data.models.responseHotel.Hotel
import com.tematihonov.hoteltest.domain.repository.NetworkRepository
import retrofit2.Response

class GetHotelUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(): Response<Hotel> {
        return networkRepository.getHotel()
    }
}