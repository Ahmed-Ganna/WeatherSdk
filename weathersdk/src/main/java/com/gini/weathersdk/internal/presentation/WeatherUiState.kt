package com.gini.weathersdk.internal.presentation

import com.gini.weathersdk.internal.presentation.model.WeatherInfoUiModel


/**
 * Represents the UI state of the weather information.
 *
 * This sealed class defines the possible states of the weather information displayed in the UI.
 * It can be in one of three states: Loading, Error, or Content.
 */
internal sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data object Error : WeatherUiState()
    data class Content(val model : WeatherInfoUiModel) : WeatherUiState()
}