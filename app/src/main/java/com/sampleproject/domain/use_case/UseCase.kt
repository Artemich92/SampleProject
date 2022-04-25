package com.sampleproject.domain.use_case

import com.sampleproject.utils.api.core.Answer
import kotlinx.coroutines.flow.Flow

abstract class UseCase<Input, Output> {
    abstract suspend operator fun invoke(params: Input): Answer<Output>
}

abstract class CommonUseCase<Input, Output> {
    abstract suspend fun get(params: Input): Flow<Output?>
    abstract suspend fun sync(params: Input): Answer<Output>
}
