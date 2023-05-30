package com.appsfactory.remote.di

import com.appsfactory.common.di.MetMuseumBaseUrl
import com.appsfactory.remote.apiservice.ArtApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val DEFAULT_READ_WRITE_TIMEOUT_IN_SECONDS = 30L

@Module
@InstallIn(SingletonComponent::class)
internal interface ApiModule {

    companion object {
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        @Provides
        @Singleton
        fun provideHttpClient(
            loggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(
                DEFAULT_READ_WRITE_TIMEOUT_IN_SECONDS,
                TimeUnit.SECONDS
            )
            .readTimeout(
                DEFAULT_READ_WRITE_TIMEOUT_IN_SECONDS,
                TimeUnit.SECONDS
            )
            .addInterceptor(loggingInterceptor)
            .build()

        @Provides
        @Singleton
        fun provideApiService(
            @MetMuseumBaseUrl baseUrl: String,
            okHttpClient: OkHttpClient
        ): ArtApiService =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(ArtApiService::class.java)
    }
}
