package com.gini.weathersdk.internal.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HourlyForecastResponse(

	@SerialName("country_code")
	val countryCode: String,

	@SerialName("city_name")
	val cityName: String,

	@SerialName("data")
	val data: List<HourlyForecastDataItem>,
)


@Serializable
internal data class HourlyForecastDataItem(

	@SerialName("timestamp_local")
	val timestampLocal: String,

	@SerialName("weather")
	val weather: Weather,

	@SerialName("temp")
	val temp: Float,

	@SerialName("ts")
	val ts: Int,

)
