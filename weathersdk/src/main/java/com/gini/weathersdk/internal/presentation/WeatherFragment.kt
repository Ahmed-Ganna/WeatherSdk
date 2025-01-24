package com.gini.weathersdk.internal.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gini.weathersdk.WeatherSDK
import com.gini.weathersdk.internal.presentation.components.LoadingView
import com.gini.weathersdk.internal.presentation.components.WeatherInfoScreenContent
import com.gini.weathersdk.internal.presentation.theme.WeatherSDKTheme


/**
 * A Fragment responsible for displaying weather information.
 *
 * This fragment uses Jetpack Compose to render the UI. It interacts with a [WeatherViewModel]
 * to fetch and manage the weather data. It can be initialized with a [WeatherViewModelFactory]
 * for dependency injection or can use a default factory.
 *
 * The fragment handles the following:
 *  - Displaying weather information in different states: Content, Loading, and Error.
 *  - Managing the back button press to navigate back.
 *  - Dismissing itself if the [WeatherViewModelFactory] is not provided.
 *  - Uses `WeatherSDKTheme` for UI theming.
 *
 * @property viewModelFactory The factory used to create the [WeatherViewModel].
 *                             If null, a default empty factory is used and the fragment will auto dismiss.
 *                             Typically provided when using `newInstance`.
 */
class WeatherFragment private constructor(private val viewModelFactory: WeatherViewModelFactory? = null) : Fragment() {

    constructor(): this(null)

    private val viewModel: WeatherViewModel by viewModels{
        viewModelFactory ?: object : ViewModelProvider.Factory {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModelFactory == null) {
            // Dismiss if the viewModelFactory is not available due to an unrecoverable state like activity recreation
            // This returns the user to the client app's previous screen.
            dismiss()
        }
    }

    private fun dismiss() {
        parentFragmentManager.popBackStackImmediate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            WeatherSDKTheme {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                when(state){
                    is WeatherUiState.Content -> WeatherInfoScreenContent(
                        model = (state as WeatherUiState.Content).model, onBackClick = ::handleBack
                    )
                    WeatherUiState.Loading -> LoadingView()
                    WeatherUiState.Error -> {
                        SideEffect {
                            dismiss()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBack()
            }
        })
    }

    private fun handleBack() {
        viewModel.onBackClick()
        dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance(config: WeatherSDK.Config, sdk : WeatherSDK) = WeatherFragment(WeatherViewModelFactory(sdk,config))
    }
}