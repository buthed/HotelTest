package com.tematihonov.hoteltest.di

import com.tematihonov.hoteltest.presentation.viewmodel.HotelViewModel
import com.tematihonov.hoteltest.presentation.viewmodel.RoomsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {


    viewModel { HotelViewModel(get()) }
    viewModel { RoomsViewModel(get()) }

    viewModelOf(::HotelViewModel)


}