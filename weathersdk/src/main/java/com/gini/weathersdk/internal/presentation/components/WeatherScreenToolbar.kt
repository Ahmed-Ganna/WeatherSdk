package com.gini.weathersdk.internal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gini.weathersdk.R
import com.gini.weathersdk.internal.presentation.theme.WeatherSDKTheme

@Composable
internal fun WeatherScreenToolbar(onBackClick : () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        IconButton(modifier = Modifier.align(Alignment.CenterStart),onClick = onBackClick){
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
        }
        Text(
            text = stringResource(R.string.wsdk_fragment_title),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
private fun WeatherScreenToolbarPreview() {
    WeatherSDKTheme {
        WeatherScreenToolbar(onBackClick = {})
    }
}