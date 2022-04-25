package com.sampleproject.domain.repository.interfaces

import com.sampleproject.domain.models.AuthModel
import com.sampleproject.utils.api.core.Answer

interface IAuthRepository {
    suspend fun logIn(phoneNumber: Long, password: String): Answer<AuthModel>
}
