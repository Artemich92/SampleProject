package com.sampleproject.data.remote.services.auth

import com.sampleproject.data.remote.api.TestIAuthApi
import com.sampleproject.data.remote.requests.auth.TestLogInRequest
import com.sampleproject.utils.api.BaseService

class TestAuthService(private val apiTest: TestIAuthApi) : TestIAuthService, BaseService() {

    override suspend fun logIn(phoneNumber: Long, password: String) = apiCall {
        apiTest.logIn(
            TestLogInRequest(
                phoneNumber,
                password
            )
        )
    }
}
