package com.gini.weathersdk

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherSDKTest {

    private val apiKey = "test_api_key"
    private val weatherSDK = WeatherSDK(apiKey)

    @Test
    fun createFragmentThrowsExceptionIfCityNameIsBlank() {
        // Arrange
        val invalidConfig = WeatherSDK.Config(cityName = "")

        // Act & Assert
        val exception = assertThrows(IllegalStateException::class.java) {
            weatherSDK.createFragment(invalidConfig)
        }
        assertEquals("City name cannot be empty", exception.message)
    }


    @Test
    fun createFragmentReturnsWeatherFragmentForValidConfig() {
        // Arrange
        val validConfig = WeatherSDK.Config(cityName = "Berlin")

        // Act
        val fragment = weatherSDK.createFragment(validConfig)

        // Assert
        assertNotNull(fragment)
    }


    @Test
    fun emitEventEmitsCorrectEvent() = runTest(UnconfinedTestDispatcher()) {
        // Arrange
        val event = WeatherSDK.WeatherSDKEvents.OnFinished
        val testSDK = WeatherSDK(apiKey)
        val deferred = async {
            testSDK.eventsFlow.first()
        }

        // Act
        testSDK.emitEvent(event)

        // Assert
        assertEquals(event, deferred.await())
    }



    @Test
    fun emitEventHandlesErrorEventsCorrectly() = runTest(UnconfinedTestDispatcher()) {
        // Arrange
        val exception = RuntimeException("Test error")
        val event = WeatherSDK.WeatherSDKEvents.OnFinishedWithError(exception)
        val testSDK = WeatherSDK(apiKey)

        val deferred = async {
            testSDK.eventsFlow.first()
        }
        // Act
        testSDK.emitEvent(event)

        // Assert
        val emittedEvent = deferred.await()
        assertTrue(emittedEvent is WeatherSDK.WeatherSDKEvents.OnFinishedWithError)
        assertEquals(exception, (emittedEvent as WeatherSDK.WeatherSDKEvents.OnFinishedWithError).e)
    }
}
