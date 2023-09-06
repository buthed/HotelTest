package com.tematihonov.hoteltest.domain.usecase.network

import com.tematihonov.hoteltest.domain.models.responceRooms.Rooms
import com.tematihonov.hoteltest.domain.repository.NetworkRepository
import retrofit2.Response

class GetRoomsUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(): Response<Rooms> {
        return networkRepository.getRooms()
    }
}