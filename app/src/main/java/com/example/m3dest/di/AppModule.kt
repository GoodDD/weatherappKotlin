package com.example.m3dest.di

import com.example.m3dest.data.remote.WeatherApi
import com.example.m3dest.repository.WeatherRepository
import com.example.m3dest.util.Constants.WEATHERAPI_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(
        api: WeatherApi
    ) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WEATHERAPI_URL)
            .build()
            .create(WeatherApi::class.java)
    }
}