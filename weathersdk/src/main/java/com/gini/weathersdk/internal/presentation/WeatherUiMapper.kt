package com.gini.weathersdk.internal.presentation

import com.gini.weathersdk.internal.domain.CurrentWeather
import com.gini.weathersdk.internal.domain.GetWeatherInfoResult
import com.gini.weathersdk.internal.domain.HourlyWeather
import com.gini.weathersdk.internal.presentation.model.CurrentWeatherUiModel
import com.gini.weathersdk.internal.presentation.model.HourlyWeatherUiModel
import com.gini.weathersdk.internal.presentation.model.WeatherInfoUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * [WeatherUiMapper] is responsible for mapping weather data from the domain layer
 * to UI-specific models. It handles the conversion of data types and formatting
 * for presentation in the user interface.
 *
 * This class provides methods to transform [GetWeatherInfoResult] (domain model)
 * into [WeatherInfoUiModel] (UI model), and includes helper functions to map
 * individual components like current weather and hourly weather forecasts.
 */
internal class WeatherUiMapper {

    fun toUiModel(domain: GetWeatherInfoResult): WeatherInfoUiModel {
        return WeatherInfoUiModel(
            currentWeather = mapToUICurrentWeather(domain.currentWeather),
            hourlyForecast = domain.hourlyForecast.map {
                mapToUIHourlyWeather(it)
            }
        )
    }

    private fun mapToUICurrentWeather(weather: CurrentWeather): CurrentWeatherUiModel {
        return CurrentWeatherUiModel(
            cityName = weather.cityName,
            temp = weather.currentTemp.toString(),
            description = weather.currentDescription,
            currentTime = formatTime(weather.currentTime)
        )
    }

    private fun mapToUIHourlyWeather(domain: HourlyWeather): HourlyWeatherUiModel {
        return HourlyWeatherUiModel(
            temp = domain.temp.toString(),
            description = domain.description,
            time = formatTime(domain.timestampLocal)
        )
    }

    /**
     * Formats a [Date] object into a string representing the time in "HH:mm" format.
     *
     * The method uses the default locale to format the provided date into a time string with hours and minutes.
     *
     * @param date The [Date] object to be formatted.
     * @return A string representing the time in "HH:mm" format.
     */
    private fun formatTime(date: Date): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}