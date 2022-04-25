package com.sampleproject.di

import com.sampleproject.utils.cash_storage.CacheStorage
import com.sampleproject.utils.cash_storage.CacheStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoreModule {

    @Provides
    @Singleton
    fun provideCashStore(): CacheStorage = CacheStorageImpl()
}
