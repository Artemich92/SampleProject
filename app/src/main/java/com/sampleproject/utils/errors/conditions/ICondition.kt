package com.sampleproject.utils.errors.conditions

import com.sampleproject.utils.ErrorWrapper

// Условие валидации.
interface ICondition<T> {
    fun validate(data: T): ErrorWrapper
}
