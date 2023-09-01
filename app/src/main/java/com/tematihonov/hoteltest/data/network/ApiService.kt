package com.tematihonov.hoteltest.data.network

import com.tematihonov.hoteltest.data.models.responceBooking.Booking
import com.tematihonov.hoteltest.data.models.responceRooms.Room
import com.tematihonov.hoteltest.data.models.responseHotel.Hotel
import com.tematihonov.hoteltest.utils.RetrofitConstants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(RetrofitConstants.HOTEL)
    suspend fun getHotel(): Response<Hotel>

    @GET(RetrofitConstants.ROOMS)
    suspend fun getRooms(): List<Room>

    @GET(RetrofitConstants.BOOKING)
    suspend fun getBooking(): Booking
}