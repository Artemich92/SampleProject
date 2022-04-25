package com.sampleproject.domain.use_case.auth

import com.sampleproject.domain.models.AuthModel
import com.sampleproject.domain.repository.interfaces.IAuthRepository
import com.sampleproject.domain.use_case.UseCase
import com.sampleproject.utils.api.core.Answer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogInUseCase(private val repository: IAuthRepository) : UseCase<LogInUseCase.Params, AuthModel>() {

    override suspend operator fun invoke(params: Params): Answer<AuthModel> {
        return withContext(Dispatchers.IO) {
            repository.logIn(params.phoneNumber, params.password)
        }
    }

    class Params(
        val phoneNumber: Long,
        val password: String
    )
}
