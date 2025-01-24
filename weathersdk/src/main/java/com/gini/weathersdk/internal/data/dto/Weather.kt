package com.gini.weathersdk.internal.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Weather(

    @SerialName("code")
    val code: Int,

    @SerialName("icon")
    val icon: String,

    @SerialName("description")
    val description: String
)