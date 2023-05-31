package com.appsfactory.remote.contractimpl

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtSearchRemote
import com.appsfactory.data.model.ArtIdEntity
import com.appsfactory.remote.mapper.ArtIdRemoteModelMapper
import com.appsfactory.remote.utils.ART_SEARCH_REQUEST_PATH
import com.appsfactory.remote.utils.ArtRequestDispatcher
import com.appsfactory.remote.utils.SEARCH_TERM
import com.appsfactory.remote.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArtSearchRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var artSearchRemote: ArtSearchRemote
    private lateinit var artIdRemoteModelMapper: ArtIdRemoteModelMapper

    @Before
    fun setup() {
        artIdRemoteModelMapper = ArtIdRemoteModelMapper()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = ArtRequestDispatcher().RequestDispatcher()
        mockWebServer.start()
        artSearchRemote = ArtSearchRemoteImpl(
            makeTestApiService(mockWebServer),
            artIdRemoteModelMapper,
        )
    }

    @Test
    fun `check that searchArtCollection returns art list`() = runBlocking {
        val artResult: Result<List<ArtIdEntity>> =
            artSearchRemote.searchArtCollection(SEARCH_TERM)
        assertThat(artResult).isInstanceOf(Result.Success::class.java)
        artResult as Result.Success
        val arts: List<ArtIdEntity> = artResult.data
        assertThat(arts).isNotEmpty()
    }

    @Test
    fun `check that calling searchArtCollection makes request to correct path`() = runBlocking {
        artSearchRemote.searchArtCollection(SEARCH_TERM)
        assertThat(ART_SEARCH_REQUEST_PATH)
            .isEqualTo(mockWebServer.takeRequest().path)
    }

    @Test
    fun `check that calling searchArtCollection makes a GET request`() = runBlocking {
        artSearchRemote.searchArtCollection(SEARCH_TERM)
        assertThat("GET $ART_SEARCH_REQUEST_PATH HTTP/1.1")
            .isEqualTo(mockWebServer.takeRequest().requestLine)
    }

    @Test
    fun `check that searchArtCollection returns proper error on bad response from server`() =
        runBlocking {
            mockWebServer.dispatcher = ArtRequestDispatcher().BadRequestDispatcher()
            val artResult: Result<List<ArtIdEntity>> =
                artSearchRemote.searchArtCollection(SEARCH_TERM)
            assertThat(artResult).isInstanceOf(Result.Error::class.java)
            artResult as Result.Error
            assertThat(artResult.failure).isInstanceOf(Failure.ServerError::class.java)
        }

    @Test
    fun `check that searchArtCollection returns proper error on server error`() =
        runBlocking {
            mockWebServer.dispatcher = ArtRequestDispatcher().ErrorRequestDispatcher()
            val artResult: Result<List<ArtIdEntity>> =
                artSearchRemote.searchArtCollection(SEARCH_TERM)
            assertThat(artResult).isInstanceOf(Result.Error::class.java)
            artResult as Result.Error
            assertThat(artResult.failure).isInstanceOf(Failure.ServerError::class.java)
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
