package com.sampleproject.di

import com.sampleproject.data.remote.api.TestIAuthApi
import com.sampleproject.data.remote.interceptors.TestAuthTokenInterceptor
import com.sampleproject.data.remote.services.auth.TestAuthService
import com.sampleproject.data.remote.services.auth.TestIAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TIMEOUT = 20L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val HTTP_SCHEME = "https://"
    private const val API_PATH = "cosmetology.joy-dev.com/api/"

    @Provides
    @Singleton
    fun provideClient(testAuthTokenInterceptor: TestAuthTokenInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().apply {
            followRedirects(false)
            followSslRedirects(false)
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(testAuthTokenInterceptor)
            addInterceptor(httpLoggingInterceptor) //Всегда должен быть последним
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("$HTTP_SCHEME$API_PATH")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideAuthApi(retrofit: Retrofit): TestIAuthApi = retrofit.create(TestIAuthApi::class.java)

    @Provides
    fun provideAuthService(apiTest: TestIAuthApi): TestIAuthService = TestAuthService(apiTest)
}
