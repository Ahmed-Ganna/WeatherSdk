package com.gini.weathersdk.internal.domain


import android.icu.util.Calendar
import android.icu.util.TimeZone
import com.gini.weathersdk.internal.data.dto.CurrentWeatherDataItem
import com.gini.weathersdk.internal.data.repo.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Interface for retrieving weather information for a given city.
 */
internal interface GetWeatherInfo{
    suspend operator fun invoke(city: String): Result<GetWeatherInfoResult>
}

/**
 * Implementation of the [GetWeatherInfo] interface.
 *
 * This class is responsible for fetching and processing weather data for a given city.
 * It retrieves both current weather conditions and a 24-hour hourly forecast.
 *
 * @property weatherRepository The repository responsible for fetching weather data from an external source.
 */
internal class GetWeatherInfoImpl(private val weatherRepository:WeatherRepository) : GetWeatherInfo {

    override suspend operator fun invoke(city: String): Result<GetWeatherInfoResult> {
        return try {
            coroutineScope {
                val currentWeatherDeferred = async { weatherRepository.getCurrentWeather(city) }
                val hourlyForecastDeferred = async { weatherRepository.getHourlyForecast(city, 24) }

                val currentWeatherResponse = currentWeatherDeferred.await()
                val hourlyForecastResponse = hourlyForecastDeferred.await()

                val currentWeather  : CurrentWeatherDataItem = currentWeatherResponse.getOrNull()?.data?.firstOrNull()
                    ?: return@coroutineScope Result.failure(Throwable("Current weather not found"))

                val hourlyForecast = hourlyForecastResponse.getOrNull()?.data?.map { dataItem ->
                    HourlyWeather(
                        temp = dataItem.temp,
                        description = dataItem.weather.description,
                        timestampLocal = parseLocalDateFormat(dataItem.timestampLocal)
                    )
                } ?: emptyList()

                val currentDate = convertToLocalDate(currentWeather.ts)

                val getWeatherInfoResult = GetWeatherInfoResult(
                    currentWeather = CurrentWeather(
                        cityName = currentWeather.cityName,
                        currentTemp = currentWeather.temp,
                        currentDescription = currentWeather.weather.description,
                        currentTime = currentDate,
                    ),
                    hourlyForecast = hourlyForecast
                )

                Result.success(getWeatherInfoResult)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    /**
     * Parses a timestamp string into a [Date] object using the local date format.
     *
     * The method expects the timestamp to be in the format `yyyy-MM-dd'T'HH:mm:ss` and attempts to parse it
     * using the default locale's date format. If parsing fails, it returns the current date.
     *
     * @param timestamp The timestamp string to be parsed.
     * @return A [Date] object representing the parsed timestamp, or the current date if parsing fails.
     */
    private fun parseLocalDateFormat(timestamp: String): Date {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        return try {
            dateFormatter.parse(timestamp)
        } catch (e: Exception) {
            Date()
        }
    }

    /**
     * Converts a timestamp in seconds to a [Date] object in the local time zone.
     *
     * The method takes a timestamp in seconds (as a `Long`), converts it to milliseconds,
     * and then adjusts the time to the local time zone.
     *
     * @param timestamp The timestamp in seconds to be converted.
     * @return A [Date] object representing the timestamp in the local time zone.
     */
    private fun convertToLocalDate(timestamp: Long): Date {
        val dateInMillis = timestamp * 1000

        val date = Date(dateInMillis)

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = date

        calendar.timeZone = TimeZone.getDefault()

        return calendar.time
    }
}

internal data class GetWeatherInfoResult(
    val currentWeather: CurrentWeather,
    val hourlyForecast:List<HourlyWeather>
)

internal data class CurrentWeather(
    val cityName: String,
    val currentTemp: Float,
    val currentDescription: String,
    val currentTime: Date,
)

internal data class HourlyWeather(
    val temp: Float,
    val description: String,
    val timestampLocal: Date
)
