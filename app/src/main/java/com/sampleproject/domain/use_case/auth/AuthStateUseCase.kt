package com.sampleproject.domain.use_case.auth

import com.sampleproject.domain.models.AuthState
import com.sampleproject.domain.repository.interfaces.IPreferencesRepository
import com.sampleproject.domain.use_case.UseCase
import com.sampleproject.utils.api.core.Answer
import com.sampleproject.utils.api.core.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber

const val LOADING_TIME = 3000L

class AuthStateUseCase(private val repository: IPreferencesRepository) : UseCase<Unit, AuthState>() {

    override suspend operator fun invoke(params: Unit): Answer<AuthState> {
        return withContext(Dispatchers.IO) {
            delay(LOADING_TIME) //TODO Временно, для старта
            try {
                when {
                    repository.token.isNullOrEmpty() -> Answer.success(AuthState.LoggedOut)
                    else -> Answer.success(AuthState.LoggedIn)
                }
            } catch (e: Exception) {
                Timber.d(e)
                Answer.failure(e, ErrorCode.InternalError)
            }
        }
    }
}
