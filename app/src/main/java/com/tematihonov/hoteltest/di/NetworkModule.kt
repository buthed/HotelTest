package com.tematihonov.hoteltest.di

import com.tematihonov.hoteltest.data.network.RetrofitFactory
import com.tematihonov.hoteltest.data.network.apiService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    factory { RetrofitFactory.provideRetrofit() }

    single { RetrofitFactory.provideNetworkApi(get()) }
    single { get<Retrofit>().apiService }
}