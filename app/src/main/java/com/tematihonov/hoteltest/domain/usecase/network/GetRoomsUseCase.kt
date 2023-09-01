package com.tematihonov.hoteltest.domain.usecase.network

import com.tematihonov.hoteltest.data.models.responceRooms.Room
import com.tematihonov.hoteltest.domain.repository.NetworkRepository

class GetRoomsUseCase(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(): List<Room> {
        return networkRepository.getRooms()
    }
}