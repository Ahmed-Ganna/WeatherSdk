package com.gini.weathersdk.internal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.gini.weathersdk.WeatherSDK
import com.gini.weathersdk.WeatherSDK.WeatherSDKEvents
import com.gini.weathersdk.internal.domain.GetWeatherInfo
import com.gini.weathersdk.internal.domain.GetWeatherInfoImpl
import com.gini.weathersdk.internal.domain.GetWeatherInfoResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * [WeatherViewModel] is a ViewModel responsible for managing the UI state related to weather information.
 * It interacts with the [WeatherSDK] to fetch weather data, handles success and error scenarios,
 * maps the data to UI models, and emits events through the SDK.
 *
 * @param sdk An instance of [WeatherSDK] to interact with the underlying weather service.
 * @param sdkConfig The configuration for the [WeatherSDK], including details like the city name.
 * @param getWeatherInfo A use case for retrieving weather information based on a city name.
 * @param uiMapper A mapper responsible for converting data models from the service layer to UI models.
 */
internal class WeatherViewModel(
    private val sdk : WeatherSDK,
    private val sdkConfig: WeatherSDK.Config,
    private val getWeatherInfo: GetWeatherInfo,
    private val uiMapper: WeatherUiMapper
): ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState : StateFlow<WeatherUiState> = _uiState

    /**
     * Initializes weather data fetching for a city.
     */
    fun initialize() {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            onError(throwable)
        }){
            val result : Result<GetWeatherInfoResult> = getWeatherInfo(sdkConfig.cityName)
            result.onSuccess {
                onSuccess(it)
            }
            result.onFailure {
                onError(it)
            }
        }
    }

    /**
     * Updates the UI with weather data on successful retrieval.
     *
     * Maps the `GetWeatherInfoResult` to `WeatherUiModel` and updates the `_uiState`
     * to `WeatherUiState.Content`.
     *
     * @param result The successfully retrieved weather data.
     */
    private fun onSuccess(result: GetWeatherInfoResult) {
        _uiState.value = WeatherUiState.Content(uiMapper.toUiModel(result))
    }

    /**
     * Handles errors encountered during weather data retrieval or processing.
     *
     * This function updates the UI state to indicate an error condition and emits an error event
     * through the SDK.
     *
     * @param throwable The Throwable representing the error that occurred. This could be an exception
     *                  thrown during data fetching, parsing, or any other operation within the weather
     *                  data handling process. The throwable will be included in the SDK event for
     *                  debugging and logging purposes.
     */
    private fun onError(throwable: Throwable) {
        _uiState.value = WeatherUiState.Error
        viewModelScope.launch {
            sdk.emitEvent(WeatherSDKEvents.OnFinishedWithError(throwable))
        }
    }

    /**
     * Handles the back button click event.
     *
     * This function is responsible for emitting an `OnFinished` event through the SDK when the
     * user interacts with a back navigation element (e.g., a back button). This signals to the
     * consuming application that the current weather-related operation or view has been completed
     * and the user intends to return to the previous state or screen.
     * @see WeatherSDKEvents.OnFinished
     */
    fun onBackClick() {
        viewModelScope.launch{
            sdk.emitEvent(WeatherSDKEvents.OnFinished)
        }
    }

}

/**
 * [WeatherViewModelFactory] is a factory class responsible for creating instances of [WeatherViewModel].
 * @property sdk The instance of [WeatherSDK] to be injected into the [WeatherViewModel].
 * @property sdkConfig The configuration for the [WeatherSDK] to be injected into the [WeatherViewModel].
 */
internal class WeatherViewModelFactory (private val sdk : WeatherSDK, private val sdkConfig: WeatherSDK.Config): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return WeatherViewModel(
            sdk = sdk,
            sdkConfig = sdkConfig,
            getWeatherInfo = GetWeatherInfoImpl(sdk.serviceLocator.getRepository()),
            uiMapper = WeatherUiMapper()
        ).apply {
            initialize()
        } as T
    }
}