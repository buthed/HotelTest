package com.tematihonov.hoteltest.di

import com.tematihonov.hoteltest.domain.usecase.NetworkUseCases
import com.tematihonov.hoteltest.domain.usecase.network.GetBookingUseCase
import com.tematihonov.hoteltest.domain.usecase.network.GetHotelUseCase
import com.tematihonov.hoteltest.domain.usecase.network.GetRoomsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {

    singleOf(::NetworkUseCases)

    single { GetHotelUseCase(get()) }
    single { GetRoomsUseCase(get()) }
    single { GetBookingUseCase(get()) }

//    single { NetworkUseCases(
//        getHotelUseCase = GetHotelUseCase(get()),
//        getRoomsUseCase = GetRoomsUseCase(get()),
//        getBookingUseCase = GetBookingUseCase(get())
//    ) }
}