package com.example.m3dest.screens.forecast

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.m3dest.components.AppTopBar
import com.example.m3dest.data.remote.responces.Forecast
import com.example.m3dest.data.remote.responces.Forecastday
import com.example.m3dest.data.remote.responces.Hour
import com.example.m3dest.screens.loading.LoadingScreen
import com.example.m3dest.util.Response
import com.google.android.gms.maps.model.LatLng
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ForecastScreen(
    latLng: LatLng,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val forecastInfo = produceState<Response<Forecast>>(initialValue = Response.Loading()) {
        value = viewModel.getForecast(latLng)
    }.value

    // TODO: Optimize this later
    //  forecastInfo.data?.let
    //  divider styling
    Scaffold(
        //topBar = { AppTopBar() }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
        ) {
            AppTopBar()
            if (forecastInfo is Response.Success) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    forecastInfo.data?.let { forecast ->
                        TemperatureSection(forecast)
                    }
                }
                Row(
                    modifier = Modifier.align(Alignment.BottomStart),
                    //verticalAlignment = Alignment.CenterVertically
                ) {
                    forecastInfo.data?.let { forecast ->
                        Column {
                            Divider(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )
                            ForecastHourlySection(forecast.forecast.forecastday[0].hour)
                            Divider(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )
                            ForecastDailySection(forecast.forecast.forecastday)
                        }
                    }
                }
//                Row(modifier = Modifier.weight(1f)) {
//                    forecastInfo.data?.let { forecast ->
//                        ForecastDailySection(forecast.forecast.forecastday)
//                    }
//                }
            } else {
                LoadingScreen()
            }
        }
    }
}

@Composable
fun TemperatureSection(forecast: Forecast) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = forecast.location.region,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "${forecast.current.temp_c}\u00B0",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = forecast.current.condition.text,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun ForecastHourlySection(hours: List<Hour>) {

    LazyRow() {
        items(items = hours) { hour : Hour ->
            ForecastCardHourly(
                hour.time,
                hour.condition.icon,
                hour.temp_c.toString()
            )
        }
    }
}

@Composable
fun ForecastCardHourly(time: String, iconUrl: String, temperature: String) {

    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                Log.i("CardItem", "https:$iconUrl")
            }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = time.takeLast(5))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:$iconUrl")
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
            Text(text = "$temperature\u00B0")
        }
    }
}

// Mocked One
@Composable
fun ForecastCardHourly() {
    
    OutlinedCard {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "20.00")
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://cdn.weatherapi.com/weather/64x64/day/176.png")
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
            Text(text = "10\u00B0")
        }
    }
}

@Composable
fun ForecastDailySection(days: List<Forecastday>) {

    LazyRow() {
        items(items = days) { day : Forecastday ->
            ForecastCardDaily(
                day.date,
                day.day.condition.icon,
                day.day.avgtemp_c.toString()
            )
        }
    }
}

@Composable
fun ForecastCardDaily(date: String, iconUrl: String, avgTemperature: String) {

    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = date.takeLast(5))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:$iconUrl")
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
            Text(text = "$avgTemperature\u00B0")
        }
    }
}

@Preview
@Composable
fun PreviewForecastCardHourly() {
    ForecastCardHourly()
}
/*@Preview(showBackground = true)
@Composable
fun PreviewTemperatureSection() {
    TemperatureSection("Tallinn", "11\u00B0", "Clear")
}*/

@Preview
@Composable
fun PreviewForecastScreen() {
    ForecastScreen(latLng = LatLng(10.00, 10.00))
}