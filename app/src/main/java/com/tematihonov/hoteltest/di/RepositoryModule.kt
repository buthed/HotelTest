package com.tematihonov.hoteltest.di

import com.tematihonov.hoteltest.data.network.ApiService
import com.tematihonov.hoteltest.data.repositoryimpl.NetworkRepositoryImpl
import com.tematihonov.hoteltest.domain.repository.NetworkRepository
import org.koin.dsl.module


val repositoryModule = module {
     fun provideNetworkRepository(api: ApiService): NetworkRepository {
          return NetworkRepositoryImpl(api)
     }
     single { provideNetworkRepository(get()) }
     single { NetworkRepositoryImpl(get()) }
}