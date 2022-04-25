package com.sampleproject.presentation.main

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sampleproject.R
import com.sampleproject.domain.use_case.auth.LogInUseCase
import com.sampleproject.utils.api.core.onFailure
import com.sampleproject.utils.api.core.onSuccess
import com.sampleproject.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

private const val TRUE_PASSWORD = "12345678"
private const val FALSE_PASSWORD = "123"
private const val PHONE_NUMBER = +71212121212

@HiltViewModel
class MainVM @Inject constructor(
    private val logInUseCase: LogInUseCase
) : BaseViewModel() {

    val isTrueResponse = MutableLiveData<Boolean?>(null)

    fun trueRequestClick() {
        loginRequest(PHONE_NUMBER, TRUE_PASSWORD)
    }

    fun falseRequestClick() {
        loginRequest(PHONE_NUMBER, FALSE_PASSWORD)
    }

    private fun loginRequest(phone: Long, password: String) {
        viewModelScope.launch {
            logInUseCase(LogInUseCase.Params(phone, password))
                .onSuccess {
                    isTrueResponse.value = true
                }.onFailure {
                    isTrueResponse.value = false
                }
        }
    }
}

@BindingAdapter("app:setBackground")
fun setBackground(view: ConstraintLayout, isTrueResponse: Boolean?) {
    when (isTrueResponse) {
        true -> view.background = ContextCompat.getDrawable(view.context, R.color.green)
        false -> view.background = ContextCompat.getDrawable(view.context, R.color.red)
        null -> view.background = ContextCompat.getDrawable(view.context, R.color.white)
    }
}
