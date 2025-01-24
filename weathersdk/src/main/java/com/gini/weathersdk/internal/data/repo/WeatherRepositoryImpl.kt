package com.gini.weathersdk.internal.data.repo

import com.gini.weathersdk.internal.data.dto.CurrentWeatherResponse
import com.gini.weathersdk.internal.data.dto.HourlyForecastResponse
import com.gini.weathersdk.internal.data.remote.WeatherRemoteDataSource

/**
 * Implementation of the [WeatherRepository] interface that fetches weather data using a remote data source.
 *
 * @property remoteDataSource The [WeatherRemoteDataSource] used to fetch weather data from a remote API.
 */
internal class WeatherRepositoryImpl(private val remoteDataSource: WeatherRemoteDataSource) : WeatherRepository {

    /**
     * Retrieves the current weather information for a specified city.
     *
     * This method fetches the data from the [remoteDataSource] and wraps the result in a [Result] object.
     * If the operation succeeds, it returns a [Result.success] containing the [CurrentWeatherResponse].
     * If an exception occurs, it returns a [Result.failure] containing the exception.
     *
     * @param city The name of the city for which the current weather data is to be fetched.
     * @return A [Result] wrapping a [CurrentWeatherResponse].
     */
    override suspend fun getCurrentWeather(city: String): Result<CurrentWeatherResponse> {
        return try {
            val response = remoteDataSource.getCurrentWeather(city)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Retrieves the hourly weather forecast for a specified city over a given number of hours.
     *
     * This method fetches the data from the [remoteDataSource] and wraps the result in a [Result] object.
     * If the operation succeeds, it returns a [Result.success] containing the [HourlyForecastResponse].
     * If an exception occurs, it returns a [Result.failure] containing the exception.
     *
     * @param city The name of the city for which the hourly forecast is to be fetched.
     * @param hours The number of hours for which the forecast data is required.
     * @return A [Result] wrapping an [HourlyForecastResponse].
     */
    override suspend fun getHourlyForecast(city: String, hours: Int): Result<HourlyForecastResponse> {
        return try {
            val response = remoteDataSource.getHourlyForecast(city, hours)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
