package com.tematihonov.hoteltest.data.network

import retrofit2.Retrofit

val Retrofit.apiService: ApiService get() = create(ApiService::class.java)