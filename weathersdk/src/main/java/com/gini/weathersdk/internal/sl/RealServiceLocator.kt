package com.gini.weathersdk.internal.sl;


import com.gini.weathersdk.internal.data.remote.WeatherApi
import com.gini.weathersdk.internal.data.remote.WeatherRemoteDataSource
import com.gini.weathersdk.internal.data.remote.interceptor.ApiKeyInterceptor
import com.gini.weathersdk.internal.data.repo.WeatherRepository
import com.gini.weathersdk.internal.data.repo.WeatherRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit;
import kotlin.jvm.java

/**
 * `RealServiceLocator` is a concrete implementation of the `ServiceLocator` interface.
 * It's responsible for providing instances of various services and dependencies needed
 * for the weather application, including the weather repository.
 *
 * This class handles the setup and configuration of the Retrofit client, OkHttp client,
 * and the API key interceptor. It uses lazy initialization to create these instances only
 * when they are first needed, optimizing performance.
 *
 * @property apikey The API key required for authenticating with the weather API. This is
 *                  passed in during the construction of the `RealServiceLocator` instance.
 * @constructor Creates a `RealServiceLocator` instance with the provided API key.
 *
 * @see ServiceLocator
 * @see WeatherRepository
 * @see WeatherApi
 * @see WeatherRepositoryImpl
 * @see WeatherRemoteDataSource
 * @see ApiKeyInterceptor
 */
internal class RealServiceLocator(private val apikey: String) : ServiceLocator {

    private val retrofit: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(ApiKeyInterceptor(apikey))
            .build()

        val networkJson = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        Retrofit.Builder()
            .baseUrl("https://api.weatherbit.io/v2.0/")
            .client(client)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val adsApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }

    private val repository: WeatherRepositoryImpl by lazy {
        WeatherRepositoryImpl(WeatherRemoteDataSource(adsApi))
    }

    override fun getRepository(): WeatherRepository {
        return repository
    }


}