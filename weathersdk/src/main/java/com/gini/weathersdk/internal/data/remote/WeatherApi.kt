package com.gini.weathersdk.internal.data.remote

import com.gini.weathersdk.internal.data.dto.CurrentWeatherResponse
import com.gini.weathersdk.internal.data.dto.HourlyForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for interacting with the weather API.
 *
 * This interface defines the endpoints and parameters for retrieving
 * weather data such as current weather conditions and hourly forecasts.
 */
internal interface WeatherApi{

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("city") city: String
    ): CurrentWeatherResponse

    @GET("forecast/hourly")
    suspend fun getHourlyForecast(@Query("city") city: String,@Query("hours") hours: Int): HourlyForecastResponse
}