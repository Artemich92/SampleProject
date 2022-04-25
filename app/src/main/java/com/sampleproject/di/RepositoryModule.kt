package com.sampleproject.di

import com.sampleproject.data.remote.services.auth.TestIAuthService
import com.sampleproject.domain.repository.TestAuthRepository
import com.sampleproject.domain.repository.interfaces.IAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(serviceTest: TestIAuthService): IAuthRepository =
        TestAuthRepository(serviceTest)
}
