package com.sampleproject.utils.errors.validators

import com.sampleproject.utils.ErrorWrapper

// Валидирует источник данных (source) по логике правил (Conditions). Итоговая ошибка вычисляется через operator
interface IValidator {
    fun validation()
    fun getState(): ErrorWrapper
}
