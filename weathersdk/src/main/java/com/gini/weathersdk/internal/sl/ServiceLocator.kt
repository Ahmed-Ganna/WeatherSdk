package com.gini.weathersdk.internal.sl;

import com.gini.weathersdk.internal.data.repo.WeatherRepository

/**
 * The `ServiceLocator` interface provides a central point for accessing application-level services.
 * It acts as a dependency injection container, allowing components to retrieve instances of required
 * services without needing to know their concrete implementations or how they are created.
 *
 * This interface defines methods to obtain specific services, ensuring a consistent and controlled
 * way to manage dependencies throughout the application.
 */
internal interface ServiceLocator {
    fun getRepository(): WeatherRepository
}