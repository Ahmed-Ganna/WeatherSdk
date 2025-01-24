package com.gini.weathersdk.internal.data.repo

import com.gini.weathersdk.internal.data.dto.CurrentWeatherResponse
import com.gini.weathersdk.internal.data.dto.HourlyForecastResponse

/**
 * A repository interface that defines methods for accessing weather data.
 *
 * This interface abstracts the data layer, allowing the implementation to fetch weather data
 * from different sources (e.g., remote or local) while maintaining a clean architecture.
 */
internal interface WeatherRepository {

    /**
     * Retrieves the current weather information for a specified city.
     *
     * @param city The name of the city for which the current weather data is to be fetched.
     * @return A [Result] wrapping a [CurrentWeatherResponse], which contains the current weather details.
     * The [Result] type can encapsulate success or failure scenarios.
     */
    suspend fun getCurrentWeather(city: String): Result<CurrentWeatherResponse>

    /**
     * Retrieves the hourly weather forecast for a specified city over a given number of hours.
     *
     * @param city The name of the city for which the hourly forecast is to be fetched.
     * @param hours The number of hours for which the forecast data is required.
     * @return A [Result] wrapping an [HourlyForecastResponse], which contains the hourly forecast details.
     * The [Result] type can encapsulate success or failure scenarios.
     */
    suspend fun getHourlyForecast(city: String, hours: Int): Result<HourlyForecastResponse>
}
