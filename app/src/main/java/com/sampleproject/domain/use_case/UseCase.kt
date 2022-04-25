package com.sampleproject.domain.use_case

import com.sampleproject.utils.api.core.Answer

abstract class UseCase<Input, Output> {
    abstract suspend operator fun invoke(params: Input): Answer<Output>
}
