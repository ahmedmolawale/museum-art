package com.appsfactory.data.di

import com.appsfactory.data.repositoryimpl.ArtDetailsRepositoryImpl
import com.appsfactory.data.repositoryimpl.ArtSearchRepositoryImpl
import com.appsfactory.domain.repository.ArtDetailsRepository
import com.appsfactory.domain.repository.ArtSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindArtRepository(
        artSearchRepositoryImpl: ArtSearchRepositoryImpl,
    ): ArtSearchRepository

    @Binds
    fun bindArtDetailsRepository(
        artDetailsRepositoryImpl: ArtDetailsRepositoryImpl,
    ): ArtDetailsRepository
}
