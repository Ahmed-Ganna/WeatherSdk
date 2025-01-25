package com.gini.weathersdk.example

import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gini.weathersdk.WeatherSDK
import com.gini.weathersdk.example.databinding.ActivityExampleBinding
import kotlinx.coroutines.launch

class ExampleActivity : AppCompatActivity() {

    private val viewModel : ExampleViewModel by viewModels(){
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ExampleViewModel(WeatherSDK("6ac62e830e334742924401be0e80573d")) as T
            }
        }
    }

    private lateinit var binding: ActivityExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setInsets()
        setContentView(binding.root)
        observeEvents()
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<MarginLayoutParams> {
                bottomMargin = insets.bottom
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.sdk.eventsFlow.collect { event ->
                        when (event) {
                            is WeatherSDK.WeatherSDKEvents.OnFinished -> Unit

                            is WeatherSDK.WeatherSDKEvents.OnFinishedWithError -> Unit
                        }
                    }
                }

                launch{
                    viewModel.cityNameFlow.collect { cityName ->
                        if (cityName.isNotEmpty()) showSdkFragment(cityName)
                        else Toast.makeText(this@ExampleActivity, "City name cant be empty", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showSdkFragment(cityName: String) {
        val fragment = viewModel.sdk.createFragment(WeatherSDK.Config(cityName))

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

}
