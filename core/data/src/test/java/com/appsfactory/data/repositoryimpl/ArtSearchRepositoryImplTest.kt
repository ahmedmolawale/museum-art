package com.appsfactory.data.repositoryimpl

import com.appsfactory.common.Result
import com.appsfactory.data.fakes.FakeArtSearchRemote
import com.appsfactory.data.mapper.ArtIdEntityMapper
import com.appsfactory.data.util.ResponseType
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtSearchRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtSearchRepositoryImplTest {

    private lateinit var artIdEntityMapper: ArtIdEntityMapper
    private lateinit var artSearchRemote: FakeArtSearchRemote
    private lateinit var artSearchRepository: ArtSearchRepository

    @Before
    fun setup() {
        artIdEntityMapper = ArtIdEntityMapper()
        artSearchRemote = FakeArtSearchRemote()
        artSearchRepository =
            ArtSearchRepositoryImpl(artSearchRemote, artIdEntityMapper)
    }

    @Test
    fun `check that searchArtCollection returns correct data`() = runTest {
        artSearchRemote.artSearchResponseType = ResponseType.DATA
        artSearchRepository.searchArtCollection("sunflower").collect {
            assertThat(it).isInstanceOf(Result.Success::class.java)
            it as Result.Success
            val artId: ArtId = it.data.first()
            assertThat(artId.value).isEqualTo(1)
        }
    }

    @Test
    fun `check that searchArtCollection returns empty on no data`() = runTest {
        artSearchRemote.artSearchResponseType = ResponseType.EMPTY_DATA
        artSearchRepository.searchArtCollection("sunflower").collect {
            assertThat(it).isInstanceOf(Result.Success::class.java)
            it as Result.Success
            assertThat(it.data).isEmpty()
        }
    }

    @Test
    fun `check that searchArtCollection returns error`() = runTest {
        artSearchRemote.artSearchResponseType = ResponseType.ERROR
        artSearchRepository.searchArtCollection("sunflower").collect {
            assertThat(it).isInstanceOf(Result.Error::class.java)
            it as Result.Error
            assertThat(it.failure).isNotNull()
        }
    }
}
