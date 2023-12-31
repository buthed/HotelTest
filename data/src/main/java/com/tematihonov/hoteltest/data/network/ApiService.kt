package com.tematihonov.hoteltest.data.network

import com.tematihonov.hoteltest.domain.models.responceBooking.Booking
import com.tematihonov.hoteltest.domain.models.responceRooms.Rooms
import com.tematihonov.hoteltest.domain.models.responseHotel.Hotel
import com.tematihonov.hoteltest.utils.RetrofitConstants
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(RetrofitConstants.HOTEL)
    suspend fun getHotel(): Response<Hotel>

    @GET(RetrofitConstants.ROOMS)
    suspend fun getRooms(): Response<Rooms>

    @GET(RetrofitConstants.BOOKING)
    suspend fun getBooking(): Response<Booking>
}