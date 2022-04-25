package com.sampleproject.domain.repository

import com.sampleproject.data.remote.responses.auth.AuthResponse
import com.sampleproject.data.remote.responses.auth.toDomain
import com.sampleproject.data.remote.services.auth.TestIAuthService
import com.sampleproject.domain.models.auth.AuthModel
import com.sampleproject.domain.repository.interfaces.IAuthRepository
import com.sampleproject.utils.api.core.Answer
import com.sampleproject.utils.api.core.map

class TestAuthRepository(
    private val serviceTest: TestIAuthService
) : IAuthRepository {

    override suspend fun logIn(phoneNumber: Long, password: String): Answer<AuthModel> {
        return serviceTest.logIn(phoneNumber, password).map(AuthResponse::toDomain)
    }
}
