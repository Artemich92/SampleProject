package com.sampleproject.presentation.main

import androidx.lifecycle.viewModelScope
import com.sampleproject.domain.use_case.auth.LogInUseCase
import com.sampleproject.presentation.main.MainContract.Action
import com.sampleproject.presentation.main.MainContract.Action.OnLogIn
import com.sampleproject.presentation.main.MainContract.Action.OnUiDestroy
import com.sampleproject.presentation.main.MainContract.Action.OnUiReady
import com.sampleproject.presentation.main.MainContract.Effect
import com.sampleproject.presentation.main.MainContract.Effect.LogIn
import com.sampleproject.presentation.main.MainContract.State
import com.sampleproject.utils.api.core.onFailure
import com.sampleproject.utils.api.core.onSuccess
import com.sampleproject.utils.base.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainVM @Inject constructor(
    private val logInUseCase: LogInUseCase
) : BaseViewModel<Action, State, Effect>() {

    override fun createInitialState() = State()

    override fun handleEvent(event: Action) {
        when (event) {
            is OnUiReady -> Unit
            is OnLogIn -> loginRequest(event.phone, event.password)
            is OnUiDestroy -> clearAllWorks()
        }
    }

    private fun loginRequest(phone: Long, password: String) {
        viewModelScope.launch {
            setState(currentState.copy(isLoading = true))
            logInUseCase(LogInUseCase.Params(phone, password))
                .onSuccess {
                    setState(currentState.copy(data = true, isLoading = false))
                    setEffect { LogIn(true) }
                }.onFailure {
                    setState(currentState.copy(data = false, isLoading = false))
                    setEffect { LogIn(false) }
                }
        }
    }
}
