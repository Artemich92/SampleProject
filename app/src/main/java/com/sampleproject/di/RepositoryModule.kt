package com.sampleproject.di

import android.content.Context
import com.sampleproject.domain.repository.PreferencesRepository
import com.sampleproject.domain.repository.interfaces.IPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(@ApplicationContext context: Context): IPreferencesRepository =
        PreferencesRepository(context)
}
