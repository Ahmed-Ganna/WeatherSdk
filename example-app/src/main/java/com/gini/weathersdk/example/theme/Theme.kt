package com.gini.weathersdk.example.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun ExampleAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            surface = IceBlue,
            onSurface = GrayishBlack,
            background = White,
            primary = DarkBlue
        ),
        content = content
    )
}
