package com.gini.weathersdk.internal.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gini.weathersdk.internal.presentation.model.CurrentWeatherUiModel
import com.gini.weathersdk.internal.presentation.model.HourlyWeatherUiModel
import com.gini.weathersdk.internal.presentation.model.WeatherInfoUiModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun WeatherInfoScreenContent(
    model : WeatherInfoUiModel,
    onBackClick : () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WeatherScreenToolbar(onBackClick)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            item{
                CurrentWeatherSection(model.currentWeather)
            }
            itemsIndexed(model.hourlyForecast){index,item->
                HourlyWeatherItem(hourlyWeather = item)
                if (index != model.hourlyForecast.lastIndex) HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
internal fun WeatherForecastScreenPreview() {
    WeatherInfoScreenContent(
        model = WeatherInfoUiModel(
            currentWeather = CurrentWeatherUiModel(
                cityName = "Cairo",
                temp = "19 C",
                description = "Sunny",
                currentTime = "12:00 Local Date"
            ),
            hourlyForecast = listOf(
                HourlyWeatherUiModel("18:00","19 C" , "Sunny"),
                HourlyWeatherUiModel("18:00","19 C" , "Sunny"),
                HourlyWeatherUiModel("18:00","19 C" , "Sunny"),
                HourlyWeatherUiModel("18:00","19 C" , "Sunny"),
                HourlyWeatherUiModel("18:00","19 C" , "Sunny"),
                HourlyWeatherUiModel("18:00","19 C" , "Sunny"),
            )
        ),
        onBackClick = {}
    )
}


