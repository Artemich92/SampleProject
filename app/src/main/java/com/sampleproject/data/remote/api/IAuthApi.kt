package com.sampleproject.data.remote.api

import com.sampleproject.data.remote.requests.auth.LogInRequest
import com.sampleproject.data.remote.responses.auth.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {

    // Login

    @POST("sessions")
    suspend fun logIn(
        @Body logInRequest: LogInRequest
    ): Response<AuthResponse>
}
