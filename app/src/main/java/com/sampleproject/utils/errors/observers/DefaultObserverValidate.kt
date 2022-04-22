package com.sampleproject.utils.errors.observers

import androidx.lifecycle.MutableLiveData
import com.sampleproject.utils.ErrorWrapper

class DefaultObserverValidate(private val error: MutableLiveData<ErrorWrapper>) : IObserveValidate {
    override fun observe(state: ErrorWrapper) {
        error.postValue(state)
    }
}
