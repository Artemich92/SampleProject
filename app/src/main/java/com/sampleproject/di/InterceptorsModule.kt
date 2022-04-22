package com.sampleproject.di

import com.sampleproject.data.remote.interceptors.AuthTokenInterceptor
import com.sampleproject.domain.repository.interfaces.IPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object InterceptorsModule {

    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(preferencesRepository: IPreferencesRepository) =
        AuthTokenInterceptor(preferencesRepository)

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
