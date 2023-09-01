package com.tematihonov.hoteltest.domain.repository

import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.data.models.responceRooms.Room
import com.tematihonov.hoteltest.data.models.responseHotel.Hotel

interface NetworkRepository {

    suspend fun getHotel(): Hotel

    suspend fun getRooms(): List<Room>

    suspend fun getBooking(): Booking
}