package com.lollipop.rdcmdt.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lollipop.rdcmdt.di.abstraction.Api
import com.lollipop.rdcmdt.helper.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilderModule {
    @Singleton
    @Provides
    fun provideMoshi(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideStethoInterceptors(): StethoInterceptor = StethoInterceptor()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        stethoInterceptor: StethoInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(stethoInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    @Api
    fun provideRetrofitApi(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .baseUrl(Constant.URL.BASE)
        .build()
}