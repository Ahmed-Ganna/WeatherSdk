package com.gini.weathersdk.internal.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
internal fun WeatherSDKTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            surface = IceBlue,
            onSurface =GrayishBlack,
            background = White
        ),
        content = content
    )
}