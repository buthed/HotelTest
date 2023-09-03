package com.tematihonov.hoteltest.data.repositoryimpl

import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.data.models.responceRooms.Rooms
import com.tematihonov.hoteltest.data.models.responseHotel.Hotel
import com.tematihonov.hoteltest.data.network.ApiService
import com.tematihonov.hoteltest.domain.repository.NetworkRepository
import retrofit2.Response

class NetworkRepositoryImpl(
    private val api: ApiService
): NetworkRepository {

    override suspend fun getHotel(): Response<Hotel> {
        return api.getHotel()
    }

    override suspend fun getRooms(): Response<Rooms> {
        return api.getRooms()
    }

    override suspend fun getBooking(): Response<Booking> {
        return api.getBooking()
    }
}