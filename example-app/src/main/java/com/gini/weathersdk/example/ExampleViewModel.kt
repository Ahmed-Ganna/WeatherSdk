package com.gini.weathersdk.example;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ExampleViewModel : ViewModel() {
    private val _cityNameFlow = MutableSharedFlow<String>()
    val cityNameFlow: SharedFlow<String> get() = _cityNameFlow

    fun onCTAClick(cityName: String) {
        viewModelScope.launch{
            _cityNameFlow.emit(cityName)
        }
    }
}