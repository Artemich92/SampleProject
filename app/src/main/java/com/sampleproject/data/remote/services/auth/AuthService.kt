package com.sampleproject.data.remote.services.auth

import com.sampleproject.data.remote.api.IAuthApi
import com.sampleproject.data.remote.requests.auth.LogInRequest
import com.sampleproject.utils.api.BaseService

class AuthService(private val api: IAuthApi) : IAuthService, BaseService() {

    override suspend fun logIn(phoneNumber: Long, password: String) = apiCall {
        api.logIn(
            LogInRequest(
                phoneNumber,
                password
            )
        )
    }
}
