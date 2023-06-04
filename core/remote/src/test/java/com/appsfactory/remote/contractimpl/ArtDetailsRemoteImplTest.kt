package com.appsfactory.remote.contractimpl

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtDetailsRemote
import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.remote.mapper.ArtDetailsRemoteModelMapper
import com.appsfactory.remote.utils.ART_DETAILS_REQUEST_PATH
import com.appsfactory.remote.utils.ART_OBJECT_ID
import com.appsfactory.remote.utils.ArtRequestDispatcher
import com.appsfactory.remote.utils.makeTestApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArtDetailsRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var artDetailsRemote: ArtDetailsRemote
    private lateinit var artDetailsRemoteModelMapper: ArtDetailsRemoteModelMapper

    @Before
    fun setup() {
        artDetailsRemoteModelMapper = ArtDetailsRemoteModelMapper()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = ArtRequestDispatcher().RequestDispatcher()
        mockWebServer.start()
        artDetailsRemote = ArtDetailsRemoteImpl(
            makeTestApiService(mockWebServer),
            artDetailsRemoteModelMapper,
        )
    }

    @Test
    fun `check that getArtDetails returns art details`() = runBlocking {
        val artDetailsResult: Result<ArtDetailsEntity> =
            artDetailsRemote.getArtDetails(ART_OBJECT_ID)
        assertThat(artDetailsResult).isInstanceOf(Result.Success::class.java)
        artDetailsResult as Result.Success
        val artDetails: ArtDetailsEntity = artDetailsResult.data
        assertThat(artDetails).isNotNull()
    }

    @Test
    fun `check that calling getArtDetails makes request to correct path`() = runBlocking {
        artDetailsRemote.getArtDetails(ART_OBJECT_ID)
        assertThat(ART_DETAILS_REQUEST_PATH)
            .isEqualTo(mockWebServer.takeRequest().path)
    }

    @Test
    fun `check that calling getArtDetails makes a GET request`() = runBlocking {
        artDetailsRemote.getArtDetails(ART_OBJECT_ID)
        assertThat("GET $ART_DETAILS_REQUEST_PATH HTTP/1.1")
            .isEqualTo(mockWebServer.takeRequest().requestLine)
    }

    @Test
    fun `check that getArtDetails returns proper error on bad response from server`() =
        runBlocking {
            mockWebServer.dispatcher = ArtRequestDispatcher().BadRequestDispatcher()
            val artResult: Result<ArtDetailsEntity> =
                artDetailsRemote.getArtDetails(ART_OBJECT_ID)
            assertThat(artResult).isInstanceOf(Result.Error::class.java)
            artResult as Result.Error
            assertThat(artResult.failure).isInstanceOf(Failure.ServerError::class.java)
        }

    @Test
    fun `check that getArtDetails returns proper error on server error`() =
        runBlocking {
            mockWebServer.dispatcher = ArtRequestDispatcher().ErrorRequestDispatcher()
            val artResult: Result<ArtDetailsEntity> =
                artDetailsRemote.getArtDetails(ART_OBJECT_ID)
            assertThat(artResult).isInstanceOf(Result.Error::class.java)
            artResult as Result.Error
            assertThat(artResult.failure).isInstanceOf(Failure.ServerError::class.java)
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
