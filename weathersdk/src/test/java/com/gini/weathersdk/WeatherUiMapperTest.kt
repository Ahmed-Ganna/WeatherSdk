package com.gini.weathersdk

import com.gini.weathersdk.internal.domain.CurrentWeather
import com.gini.weathersdk.internal.domain.GetWeatherInfoResult
import com.gini.weathersdk.internal.domain.HourlyWeather
import com.gini.weathersdk.internal.presentation.WeatherUiMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class WeatherUiMapperTest {

    private val weatherUiMapper = WeatherUiMapper()

    @Test
    fun toUiModelShouldMapDomainModelToUIModelCorrectly() {
        val currentWeather = CurrentWeather(
            cityName = "Berlin",
            currentTemp = 22.5f,
            currentDescription = "Sunny",
            currentTime = createMockDate("15:30")
        )
        val hourlyForecast = listOf(
            HourlyWeather(
                temp = 20.0f,
                description = "Clear sky",
                timestampLocal = createMockDate("16:00")
            ),
            HourlyWeather(
                temp = 18.5f,
                description = "Partly cloudy",
                timestampLocal = createMockDate("17:00")
            )
        )
        val domainModel = GetWeatherInfoResult(
            currentWeather = currentWeather,
            hourlyForecast = hourlyForecast
        )

        // Act: Map to UI model
        val uiModel = weatherUiMapper.toUiModel(domainModel)

        // Assert: Verify the mapping logic
        assertEquals("Berlin", uiModel.currentWeather.cityName)
        assertEquals("22.5", uiModel.currentWeather.temp)
        assertEquals("Sunny", uiModel.currentWeather.description)
        assertEquals("15:30", uiModel.currentWeather.currentTime)

        assertEquals(2, uiModel.hourlyForecast.size)

        assertEquals("20.0", uiModel.hourlyForecast[0].temp)
        assertEquals("Clear sky", uiModel.hourlyForecast[0].description)
        assertEquals("16:00", uiModel.hourlyForecast[0].time)

        assertEquals("18.5", uiModel.hourlyForecast[1].temp)
        assertEquals("Partly cloudy", uiModel.hourlyForecast[1].description)
        assertEquals("17:00", uiModel.hourlyForecast[1].time)
    }

    // Helper function to create mock Date objects
    private fun createMockDate(time: String): Date {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.parse(time)!!
    }
}
