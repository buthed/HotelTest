package com.tematihonov.hoteltest.domain.repository

import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.data.models.responceRooms.Room
import com.tematihonov.hoteltest.data.models.responseHotel.Hotel
import retrofit2.Response

interface NetworkRepository {

    suspend fun getHotel(): Response<Hotel>

    suspend fun getRooms(): List<Room>

    suspend fun getBooking(): Booking
}