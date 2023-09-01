package com.tematihonov.hoteltest.domain.usecase.network

import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.domain.repository.NetworkRepository

class GetBookingUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(): Booking {
        return networkRepository.getBooking()
    }
}