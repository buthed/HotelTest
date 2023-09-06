package com.tematihonov.hoteltest.domain.repository

import com.tematihonov.hoteltest.domain.models.responceBooking.Booking
import com.tematihonov.hoteltest.domain.models.responceRooms.Rooms
import com.tematihonov.hoteltest.domain.models.responseHotel.Hotel
import retrofit2.Response

interface NetworkRepository {

    suspend fun getHotel(): Response<Hotel>

    suspend fun getRooms(): Response<Rooms>

    suspend fun getBooking(): Response<Booking>
}