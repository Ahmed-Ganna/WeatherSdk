package com.gini.weathersdk

import com.gini.weathersdk.internal.presentation.WeatherFragment
import com.gini.weathersdk.internal.sl.RealServiceLocator
import com.gini.weathersdk.internal.sl.ServiceLocator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * The main class for interacting with the Weather SDK.
 *
 * This class provides functionalities for creating a weather fragment and receiving events related to the weather data.
 *
 * @property apiKey The API key used for authenticating with the weather service.
 * @constructor Creates a new WeatherSDK instance with the provided API key.
 */
class WeatherSDK (private val apiKey: String) {

    internal val serviceLocator: ServiceLocator by lazy {
        RealServiceLocator(apiKey)
    }

    /**
     * A private mutable shared flow used to emit events related to the Weather SDK.
     */
    private val _eventsFlow = MutableSharedFlow<WeatherSDKEvents>()

    /**
     * A public read-only shared flow that exposes events related to the Weather SDK.
     * Consumers can collect this flow to be informed about events .
     *
     * @see WeatherSDKEvents
     */
    val eventsFlow: SharedFlow<WeatherSDKEvents> = _eventsFlow

    /**
     * Creates a new instance of [WeatherFragment] with the provided configuration.
     *
     * This function serves as a factory method for creating [WeatherFragment] instances.
     * It ensures that the required configuration, such as the city name, is valid before
     * creating the fragment. If the city name is blank, an error will be thrown.
     *
     * @param config The configuration for the [WeatherFragment], including the city name.
     *               Must contain a non-blank city name.
     * @return A new instance of [WeatherFragment] configured with the provided `config`.
     * @throws IllegalStateException if the city name in the provided `config` is blank.
     */
    fun createFragment(config: Config): WeatherFragment {
        if (config.cityName.isBlank()) error("City name cannot be empty")
        return WeatherFragment.newInstance(config,this)
    }

    /**
     * Emits a [WeatherSDKEvents] to the internal event flow.
     *
     * This function is responsible for sending events to the `_eventsFlow`, allowing
     * other parts of the system to react to these events. It's designed for
     * internal use within the Weather SDK.
     *
     * @see WeatherSDKEvents
     * @throws Exception if there is any issue emitting the event to the flow.
     */
    internal suspend fun emitEvent(event: WeatherSDKEvents) {
        _eventsFlow.emit(event)
    }

    /**
     * `WeatherSDKEvents` is a sealed interface representing events emitted by the Weather SDK.
     * It provides a type-safe way to handle different outcomes of asynchronous operations,
     * such as successful completion or completion with an error.
     */
    sealed interface WeatherSDKEvents {
        /**
         * Represents the event when the user taps the back button
         * and the SDK's fragment can be dismissed.
         */
        data object OnFinished: WeatherSDKEvents

        /**
         * Represents the event when an error occurs, and the SDK's fragment was dismissed.
         *
         * @property e The exception describing the error that occurred.
         */
        data class OnFinishedWithError(val e: Throwable): WeatherSDKEvents
    }

    /**
     * Represents the configuration for the SDK.
     *
     * This data class holds key configuration parameters that dictate the behavior of the SDK,
     * such as the target city for weather data retrieval.
     *
     * @property cityName The name of the city for which weather information will be fetched.
     */
    data class Config(
        val cityName: String
    )
}

