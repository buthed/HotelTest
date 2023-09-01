package com.tematihonov.hoteltest.di

import org.koin.dsl.module

val appModule = module {
    includes(
        networkModule,
        viewModelModule,
        repositoryModule,
        useCaseModule
    )
}