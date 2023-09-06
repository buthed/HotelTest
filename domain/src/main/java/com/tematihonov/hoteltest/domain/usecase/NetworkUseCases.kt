package com.tematihonov.hoteltest.domain.usecase

import com.tematihonov.hoteltest.domain.usecase.network.GetBookingUseCase
import com.tematihonov.hoteltest.domain.usecase.network.GetHotelUseCase
import com.tematihonov.hoteltest.domain.usecase.network.GetRoomsUseCase

data class NetworkUseCases(
    val getHotelUseCase: GetHotelUseCase,
    val getRoomsUseCase: GetRoomsUseCase,
    val getBookingUseCase: GetBookingUseCase
)
