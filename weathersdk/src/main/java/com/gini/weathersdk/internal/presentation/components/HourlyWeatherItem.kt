package com.gini.weathersdk.internal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gini.weathersdk.R
import com.gini.weathersdk.internal.presentation.model.HourlyWeatherUiModel
import com.gini.weathersdk.internal.presentation.theme.WeatherSDKTheme


@Composable
internal fun HourlyWeatherItem(hourlyWeather: HourlyWeatherUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hourlyWeather.time,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.wsdk_temp,hourlyWeather.temp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = hourlyWeather.description,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HourlyWeatherItemPreview() {
    WeatherSDKTheme {
        HourlyWeatherItem(
            hourlyWeather = HourlyWeatherUiModel(
                time = "18:00",
                temp = "19 C",
                description = "Sunny"
            )
        )
    }
}