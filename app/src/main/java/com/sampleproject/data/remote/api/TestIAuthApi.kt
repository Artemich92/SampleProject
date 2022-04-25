package com.sampleproject.data.remote.api

import com.sampleproject.data.remote.requests.auth.TestLogInRequest
import com.sampleproject.data.remote.responses.auth.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TestIAuthApi {

    // TestLogin
    @POST("sessions")
    suspend fun logIn(
        @Body testLogInRequest: TestLogInRequest
    ): Response<AuthResponse>
}
