package com.gini.weathersdk.internal.data.remote.interceptor;

import okhttp3.Interceptor;
import okhttp3.Response

/**
 * An OkHttp interceptor that adds an API key as a query parameter to every outgoing request.
 *
 * This interceptor ensures that the provided API key is included in the URL of each HTTP request made
 * by the OkHttp client it is attached to. The API key is added as a query parameter named "key".
 *
 * @property apiKey The API key to be added to each request.
 *
 * @constructor Creates an instance of ApiKeyInterceptor with the specified API key.
 */
internal class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}