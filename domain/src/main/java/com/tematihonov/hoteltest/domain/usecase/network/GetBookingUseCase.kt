package com.tematihonov.hoteltest.domain.usecase.network

import com.tematihonov.hoteltest.domain.models.responceBooking.Booking
import com.tematihonov.hoteltest.domain.repository.NetworkRepository
import retrofit2.Response

class GetBookingUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(): Response<Booking> {
        return networkRepository.getBooking()
    }
}