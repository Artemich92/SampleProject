package com.sampleproject.di.use_cases

import com.sampleproject.domain.repository.interfaces.IAuthRepository
import com.sampleproject.domain.use_case.auth.LogInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TestAuthUseCasesModule {

    @Provides
    fun provideAuthUseCase(iAuthRepository: IAuthRepository) = LogInUseCase(iAuthRepository)
}
