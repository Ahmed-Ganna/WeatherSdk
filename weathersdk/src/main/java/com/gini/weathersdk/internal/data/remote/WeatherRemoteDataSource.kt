package com.gini.weathersdk.internal.data.remote

import com.gini.weathersdk.internal.data.dto.CurrentWeatherResponse
import com.gini.weathersdk.internal.data.dto.HourlyForecastResponse


/**
 * A data source class responsible for fetching weather data from a remote API.
 *
 * @property weatherApi The [WeatherApi] instance used to make network requests.
 */
internal class WeatherRemoteDataSource(private val weatherApi: WeatherApi) {

    /**
     * Fetches the current weather information for a specified city.
     *
     * @param city The name of the city for which the current weather data is to be fetched.
     * @return A [CurrentWeatherResponse] object containing the current weather details.
     * @throws Exception If the API call fails.
     */
    suspend fun getCurrentWeather(city: String): CurrentWeatherResponse {
        return weatherApi.getCurrentWeather(city)
    }

    /**
     * Fetches the hourly weather forecast for a specified city over a given number of hours.
     *
     * @param city The name of the city for which the hourly forecast is to be fetched.
     * @param hours The number of hours for which the forecast data is needed.
     * @return An [HourlyForecastResponse] object containing the forecast details.
     * @throws Exception If the API call fails.
     */
    suspend fun getHourlyForecast(city: String, hours: Int): HourlyForecastResponse {
        return weatherApi.getHourlyForecast(city, hours)
    }
}