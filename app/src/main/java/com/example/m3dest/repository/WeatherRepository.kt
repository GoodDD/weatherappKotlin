package com.example.m3dest.repository

import com.example.m3dest.data.remote.WeatherApi
import com.example.m3dest.data.remote.responces.Forecast
import com.example.m3dest.util.Response
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherRepository @Inject constructor(
    private val api: WeatherApi
) {

    suspend fun getForecast(latLng: String): Response<Forecast> {
        val response = try {
            api.getForecast(latLng = latLng)
        } catch (e: Exception) {
            return Response.Error(e.message!!)
        }
        return Response.Success(response)
    }
}