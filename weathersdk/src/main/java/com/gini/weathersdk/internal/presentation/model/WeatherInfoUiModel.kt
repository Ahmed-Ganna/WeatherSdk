package com.gini.weathersdk.internal.presentation.model


/**
 * Represents the weather information to be displayed in the UI.
 *
 * This class aggregates the current weather conditions and the hourly forecast into a single
 * data model that can be easily consumed by the UI layer.
 *
 * @property currentWeather The current weather conditions, encapsulated in a [CurrentWeatherUiModel].
 * @property hourlyForecast A list of [HourlyWeatherUiModel] objects, each representing the weather forecast for a specific hour.
 */
internal data class WeatherInfoUiModel(
    val currentWeather: CurrentWeatherUiModel,
    val hourlyForecast: List<HourlyWeatherUiModel>
)

internal data class CurrentWeatherUiModel(
    val cityName: String,
    val temp: String,
    val description: String,
    val currentTime: String,
)

internal data class HourlyWeatherUiModel(
    val temp: String,
    val description: String,
    val time: String
)