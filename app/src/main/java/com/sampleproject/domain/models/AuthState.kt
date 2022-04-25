package com.sampleproject.domain.models

sealed class AuthState {
    object LoggedIn : AuthState()
    object LoggedOut : AuthState()
}
