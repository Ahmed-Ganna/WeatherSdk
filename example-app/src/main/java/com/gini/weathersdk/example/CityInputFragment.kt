package com.gini.weathersdk.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gini.weathersdk.example.theme.ExampleAppTheme

class CityInputFragment : Fragment() {

    private val activityViewModel by activityViewModels<ExampleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply{
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            ExampleAppTheme {
                CityInputScreenContent(
                    onCtaClick = { cityName ->
                        activityViewModel.onCTAClick(cityName)
                    }
                )
            }
        }
    }
}

@Composable
private fun CityInputScreenContent(onCtaClick: (String) -> Unit) {
    // State to hold the entered city name
    var cityName by rememberSaveable { mutableStateOf("") }

    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App title
        Box(modifier = Modifier.fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surface)) {
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // City name input
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = cityName,
                onValueChange = { cityName = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                label = {
                    Text(text = stringResource(R.string.city_name_label))
                },
                supportingText = {
                    Text(text = stringResource(R.string.enter_city_name))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button
        Button(
            onClick = { onCtaClick(cityName) },
            shape = RoundedCornerShape(25.dp),
        ) {
            Text(text = stringResource(R.string.button_forecast), fontSize = 16.sp)
        }
    }
}


@Preview
@Composable
private fun CityInputScreenContentPreview() {
    ExampleAppTheme {
        CityInputScreenContent(onCtaClick = {})
    }
}