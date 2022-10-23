package com.example.m3dest.screens.forecast

import androidx.lifecycle.ViewModel
import com.example.m3dest.data.remote.responces.Forecast
import com.example.m3dest.repository.WeatherRepository
import com.example.m3dest.util.Response
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {

    suspend fun getForecast(latLng: LatLng): Response<Forecast> {
        val latLngString = "${latLng.latitude},${latLng.longitude}"
        return repository.getForecast(latLngString)
    }
}