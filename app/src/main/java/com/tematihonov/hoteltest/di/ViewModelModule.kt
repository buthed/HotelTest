package com.tematihonov.hoteltest.di

import com.tematihonov.hoteltest.presentation.viewmodel.HotelViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {


    viewModel { HotelViewModel(get()) }

    viewModelOf(::HotelViewModel)


}