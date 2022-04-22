package com.sampleproject.utils.errors.operators

import com.sampleproject.utils.ErrorWrapper

// Тоже самое, что и IOperator, но с учетом того, что ошибки могут поовторяться по валидаторам
interface IOperatorMux {
    fun getError(validationResult: List<ErrorWrapper>): ErrorWrapper
}
