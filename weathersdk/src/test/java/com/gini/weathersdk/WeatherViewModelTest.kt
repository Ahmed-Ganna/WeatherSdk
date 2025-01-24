package com.gini.weathersdk

import com.gini.weathersdk.WeatherSDK.WeatherSDKEvents
import com.gini.weathersdk.internal.domain.GetWeatherInfo
import com.gini.weathersdk.internal.presentation.WeatherUiMapper
import com.gini.weathersdk.internal.presentation.WeatherUiState
import com.gini.weathersdk.internal.presentation.WeatherViewModel
import com.gini.weathersdk.internal.presentation.model.WeatherInfoUiModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
internal class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getWeatherInfo: GetWeatherInfo
    @Mock
    private lateinit var sdk : WeatherSDK

    private val sdkConfig = WeatherSDK.Config(cityName = "Munich")


    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun uiStateEmitsContentWhenGetWeatherInfoSucceeds() = runTest() {
        val uiMapper = mock<WeatherUiMapper>()

        val mockedUiModel = mock<WeatherInfoUiModel>()

        whenever(getWeatherInfo.invoke(any())).thenReturn(Result.success(mock()))
        whenever(uiMapper.toUiModel(any())).thenReturn(mockedUiModel)

        var viewModel = WeatherViewModel(mock(), sdkConfig,getWeatherInfo,uiMapper)

        assertEquals(viewModel.uiState.value,WeatherUiState.Loading)

        viewModel.initialize()

        assertEquals(viewModel.uiState.value,WeatherUiState.Content(mockedUiModel))
    }


    @Test
    fun uiStateEmitsErrorAndSdkEmitsOnFinishedWithErrorWhenGetWeatherInfoFails() = runTest {

        val exception = Exception("Network error")

        whenever(getWeatherInfo.invoke(any())).thenReturn(Result.failure(exception))

        var viewModel = WeatherViewModel(sdk, sdkConfig,getWeatherInfo,mock())

        assertEquals(viewModel.uiState.value,WeatherUiState.Loading)

        viewModel.initialize()

        assertEquals(viewModel.uiState.value,WeatherUiState.Error)

        verify(sdk).emitEvent(check {
            assertTrue(it is WeatherSDKEvents.OnFinishedWithError)
            assertEquals(exception, (it as WeatherSDKEvents.OnFinishedWithError).e)
        })
    }


    @Test
    fun sdkEmitsOnFinishedWhenOnBackClickIsCalled() = runTest {
        var viewModel = WeatherViewModel(sdk, sdkConfig,getWeatherInfo,mock())

        viewModel.onBackClick()

        verify(sdk).emitEvent(WeatherSDKEvents.OnFinished)
    }
}
