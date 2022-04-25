package com.sampleproject.data.remote.services.auth

import com.sampleproject.data.remote.responses.auth.AuthResponse
import com.sampleproject.utils.api.core.Answer

interface IAuthService {
    suspend fun logIn(phoneNumber: Long, password: String): Answer<AuthResponse>
}
