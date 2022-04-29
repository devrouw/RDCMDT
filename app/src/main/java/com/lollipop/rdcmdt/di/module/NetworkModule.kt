package com.lollipop.rdcmdt.di.module

import com.lollipop.rdcmdt.di.abstraction.Api
import com.lollipop.rdcmdt.service.network.ApiNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(@Api retrofit: Retrofit): ApiNetwork =
        retrofit.create(ApiNetwork::class.java)
}