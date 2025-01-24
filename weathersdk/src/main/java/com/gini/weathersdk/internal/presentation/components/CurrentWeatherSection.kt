package com.gini.weathersdk.internal.presentation.components;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gini.weathersdk.R
import com.gini.weathersdk.internal.presentation.model.CurrentWeatherUiModel
import com.gini.weathersdk.internal.presentation.theme.WeatherSDKTheme

@Composable
internal fun CurrentWeatherSection(currentWeather: CurrentWeatherUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp , vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.wsdk_fragment_subtitle,currentWeather.cityName),
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.wsdk_temp,currentWeather.temp),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = currentWeather.description,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.wsdk_local_time,currentWeather.currentTime),
        )
    }
}

@Preview
@Composable
private fun CurrentWeatherSectionPreview() {
    WeatherSDKTheme {
        CurrentWeatherSection(
            currentWeather = CurrentWeatherUiModel(
                cityName = "Cairo",
                temp = "19 C",
                description = "Sunny",
                currentTime = "12:00 Local Date"
            )
        )
    }
}