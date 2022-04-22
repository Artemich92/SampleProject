package com.sampleproject.utils.errors.operators

import com.sampleproject.utils.ErrorWrapper

// Валидировать поле можно используя несколько правил (Condition). Оператор решает, какая ошибка в итоге получается
interface IOperator {
    fun getError(validationResult: Set<ErrorWrapper>): ErrorWrapper
}
