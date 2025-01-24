package com.gini.weathersdk.internal.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentWeatherResponse(

	@SerialName("data")
	val data: List<CurrentWeatherDataItem>,

)

@Serializable
internal data class CurrentWeatherDataItem(

	@SerialName("temp")
	val temp: Float,

	@SerialName("city_name")
	val cityName: String,

	@SerialName("weather")
	val weather: Weather,

	@SerialName("ts")
	val ts: Long
)
