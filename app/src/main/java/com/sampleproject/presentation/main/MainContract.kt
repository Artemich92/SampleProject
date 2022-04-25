package com.sampleproject.presentation.main

import com.sampleproject.utils.base.mvi.UiAction
import com.sampleproject.utils.base.mvi.UiEffect
import com.sampleproject.utils.base.mvi.UiState

interface MainContract {

    sealed interface Action : UiAction {
        object OnUiReady : Action
        data class OnLogIn(val phone: Long, val password: String) : Action
        object OnUiDestroy : Action
    }

    data class State(
        val data: Boolean? = null,
        val isLoading: Boolean = false
    ) : UiState

    sealed interface Effect : UiEffect {
        data class LogIn(val isLogin: Boolean): Effect
    }
}