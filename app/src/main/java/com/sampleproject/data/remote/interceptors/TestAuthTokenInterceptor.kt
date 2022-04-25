package com.sampleproject.data.remote.interceptors

import com.sampleproject.domain.repository.interfaces.IPreferencesRepository
import okhttp3.Interceptor
import okhttp3.Response

class TestAuthTokenInterceptor(private val preferences: IPreferencesRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //TODO Обработка скисшего токена

        val token = preferences.token
        val originalRequest = chain.request()

        val newRequest = if (token.isNullOrEmpty()) {
            originalRequest.newBuilder().build()
        } else {
            originalRequest.newBuilder()
                .addHeader("Accept", "application/json") // Нужно ли это ?
                .addHeader("Authorization", token)
                .build()
        }

        val response = chain.proceed(newRequest)
        val tokenFromHeader = response.headers["authorization"]
        if (!tokenFromHeader.isNullOrEmpty()) {
            preferences.token = tokenFromHeader
        }

        return response
    }
}
