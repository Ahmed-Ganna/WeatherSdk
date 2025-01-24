package com.gini.weathersdk;

import com.gini.weathersdk.internal.data.dto.CurrentWeatherResponse
import com.gini.weathersdk.internal.data.dto.HourlyForecastResponse
import com.gini.weathersdk.internal.data.remote.WeatherRemoteDataSource
import com.gini.weathersdk.internal.data.repo.WeatherRepository
import com.gini.weathersdk.internal.data.repo.WeatherRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class WeatherRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: WeatherRemoteDataSource
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        weatherRepository = WeatherRepositoryImpl(remoteDataSource)
    }

    @Test
    fun getCurrentWeatherShouldReturnResultFromRemoteDataSource() = runTest {
        // Arrange
        val city = "Munich"
        val expectedResponse = mock<CurrentWeatherResponse>()
        whenever(remoteDataSource.getCurrentWeather(city)).thenReturn(expectedResponse)

        // Act
        val result = weatherRepository.getCurrentWeather(city)

        // Assert
        verify(remoteDataSource).getCurrentWeather(city) // Verify the method was called
        assertEquals(expectedResponse, result.getOrNull()) // Check the response matches
    }

    @Test
    fun getHourlyForecastShouldReturnResultFromRemoteDataSource() = runTest {
        // Arrange
        val city = "Berlin"
        val hours = 5
        val expectedResponse = mock<HourlyForecastResponse>()
        whenever(remoteDataSource.getHourlyForecast(city, hours)).thenReturn(expectedResponse)

        // Act
        val result = weatherRepository.getHourlyForecast(city, hours)

        // Assert
        verify(remoteDataSource).getHourlyForecast(city, hours) // Verify the method was called
        assertEquals(expectedResponse, result.getOrNull()) // Check the response matches
    }

    @Test
    fun getCurrentWeatherShouldReturnResultFailureWhenDataSourceFails() = runTest {
        // Arrange
        val city = "Berlin"
        val expectedError = RuntimeException("Network error")
        whenever(remoteDataSource.getCurrentWeather(city)).thenThrow(expectedError)

        // Act
        val result = weatherRepository.getCurrentWeather(city).exceptionOrNull()

        // Assert
        verify(remoteDataSource).getCurrentWeather(city) // Ensure data source was called
        assertEquals(expectedError, result) // Ensure the failure is propagated correctly
    }

    @Test
    fun getHourlyForecastShouldReturnResultFailureWhenDataSourceFails() = runTest {
        // Arrange
        val city = "Berlin"
        val hours = 5
        val expectedError = RuntimeException("API error")
        whenever(remoteDataSource.getHourlyForecast(city, hours)).thenThrow(expectedError)

        // Act
        val result = weatherRepository.getHourlyForecast(city, hours).exceptionOrNull()

        // Assert
        verify(remoteDataSource).getHourlyForecast(city, hours) // Ensure data source was called
        assertEquals(expectedError, result) // Ensure the failure is propagated correctly
    }


}
