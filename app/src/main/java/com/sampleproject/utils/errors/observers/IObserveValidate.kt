package com.sampleproject.utils.errors.observers

import com.sampleproject.utils.ErrorWrapper

// Подписчик на изменение состояние валидатора
interface IObserveValidate {
    fun observe(state: ErrorWrapper)
}
