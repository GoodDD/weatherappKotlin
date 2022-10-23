package com.example.m3dest.data.remote

import com.example.m3dest.BuildConfig.WEATHERAPI_KEY
import com.example.m3dest.data.remote.responces.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") key: String = WEATHERAPI_KEY,
        @Query("q") latLng: String,
        @Query("days") days: String = "7"
    ): Forecast
}