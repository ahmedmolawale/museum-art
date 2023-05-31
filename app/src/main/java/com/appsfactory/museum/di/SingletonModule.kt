package com.appsfactory.museum.di

import com.appsfactory.common.di.MetMuseumBaseUrl
import com.appsfactory.museum.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class SingletonModule {

    @Provides
    @MetMuseumBaseUrl
    fun provideBaseUrl(): String = BuildConfig.MET_MUSEUM_BASE_URL
}
