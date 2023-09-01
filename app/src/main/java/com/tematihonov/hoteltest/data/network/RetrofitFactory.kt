package com.tematihonov.hoteltest.data.network

import com.tematihonov.hoteltest.domain.repository.NetworkRepository
import com.tematihonov.hoteltest.utils.RetrofitConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(RetrofitConstants.BASE_URL)
        .build()

    fun provideNetworkApi(retrofit: Retrofit) {
        retrofit.create(NetworkRepository::class.java)
    }
}