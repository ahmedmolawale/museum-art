package com.appsfactory.data.repositoryimpl

import com.appsfactory.common.Result
import com.appsfactory.data.fakes.FakeArtDetailsRemote
import com.appsfactory.data.fakes.artDetailsEntity
import com.appsfactory.data.mapper.ArtDetailsEntityMapper
import com.appsfactory.data.util.ResponseType
import com.appsfactory.domain.model.ArtDetails
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtDetailsRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtDetailsRepositoryImplTest {

    private lateinit var artDetailsEntityMapper: ArtDetailsEntityMapper
    private lateinit var artDetailsRemote: FakeArtDetailsRemote
    private lateinit var artDetailsRepository: ArtDetailsRepository

    @Before
    fun setup() {
        artDetailsEntityMapper = ArtDetailsEntityMapper()
        artDetailsRemote = FakeArtDetailsRemote()
        artDetailsRepository =
            ArtDetailsRepositoryImpl(artDetailsRemote, artDetailsEntityMapper)
    }

    @Test
    fun `check that getArtDetails returns correct data`() = runTest {
        artDetailsRemote.artDetailsResponseType = ResponseType.DATA
        artDetailsRepository.getArtDetails(ArtId(1)).collect {
            assertThat(it).isInstanceOf(Result.Success::class.java)
            it as Result.Success
            val artDetails: ArtDetails = it.data
            assertThat(artDetails.headline).isEqualTo(artDetailsEntity.objectName)
            assertThat(artDetails.title).isEqualTo(artDetailsEntity.title)
            assertThat(artDetails.displayBio).isEqualTo(artDetailsEntity.artistDisplayBio)
            assertThat(artDetails.displayName).isEqualTo(artDetailsEntity.artistDisplayName)
            assertThat(artDetails.primaryImage).isEqualTo(artDetailsEntity.primaryImage)
            assertThat(artDetails.additionalImages).isEqualTo(artDetailsEntity.additionalImages)
        }
    }

    @Test
    fun `check that getArtDetails returns error`() = runTest {
        artDetailsRemote.artDetailsResponseType = ResponseType.ERROR
        artDetailsRepository.getArtDetails(ArtId(1)).collect {
            assertThat(it).isInstanceOf(Result.Error::class.java)
            it as Result.Error
            assertThat(it.failure).isNotNull()
        }
    }
}
