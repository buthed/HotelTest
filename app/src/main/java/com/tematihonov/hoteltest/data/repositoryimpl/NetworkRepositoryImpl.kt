package com.tematihonov.hoteltest.data.repositoryimpl

import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.data.models.responceRooms.Room
import com.tematihonov.hoteltest.data.models.responseHotel.Hotel
import com.tematihonov.hoteltest.data.network.ApiService
import com.tematihonov.hoteltest.domain.repository.NetworkRepository

class NetworkRepositoryImpl(
    private val api: ApiService
): NetworkRepository {

    override suspend fun getHotel(): Hotel {
        return api.getHotel()
    }

    override suspend fun getRooms(): List<Room> {
        return api.getRooms()
    }

    override suspend fun getBooking(): Booking {
        return api.getBooking()
    }
}