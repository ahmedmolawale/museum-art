package com.appsfactory.museum.features.arts

import com.appsfactory.museum.MainDispatcherRule
import com.appsfactory.museum.ResponseType
import com.appsfactory.museum.fakes.FakeArtSearchRepository
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.ArtCollection
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.EmptyArtCollection
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.EndLoading
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.Error
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.StartLoading
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtSearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var fakeArtSearchRepository: FakeArtSearchRepository
    private lateinit var artSearchViewModel: ArtSearchViewModel

    @Before
    fun setup() {
        fakeArtSearchRepository = FakeArtSearchRepository()
        artSearchViewModel = ArtSearchViewModel(
            fakeArtSearchRepository,
        )
    }

    @Test
    fun `check that getArtCollection returns correct data`() = runTest {
        fakeArtSearchRepository.artListResponseType = ResponseType.DATA

        val expectedStates = mutableListOf<ArtSearchViewModel.UiStateModel>()
        val job = launch(UnconfinedTestDispatcher()) {
            artSearchViewModel.uiState.toList(expectedStates)
        }
        artSearchViewModel.getArtCollection("sunflower")
        advanceUntilIdle()

        assertThat(expectedStates.map { it.javaClass }).containsAtLeast(
            StartLoading::class.java,
            EndLoading::class.java,
            ArtCollection::class.java,
        )
        job.cancel()
    }

    @Test
    fun `check that getArtCollection returns empty data`() = runTest {
        fakeArtSearchRepository.artListResponseType = ResponseType.EMPTY_DATA

        val expectedStates = mutableListOf<ArtSearchViewModel.UiStateModel>()
        val job = launch(UnconfinedTestDispatcher()) {
            artSearchViewModel.uiState.toList(expectedStates)
        }
        artSearchViewModel.getArtCollection("sunflower")
        advanceUntilIdle()

        assertThat(expectedStates.map { it.javaClass }).containsAtLeast(
            StartLoading::class.java,
            EndLoading::class.java,
            EmptyArtCollection::class.java,
        )
        job.cancel()
    }

    @Test
    fun `check that getArtCollection returns error`() = runTest {
        fakeArtSearchRepository.artListResponseType = ResponseType.ERROR

        val expectedStates = mutableListOf<ArtSearchViewModel.UiStateModel>()
        val job = launch(UnconfinedTestDispatcher()) {
            artSearchViewModel.uiState.toList(expectedStates)
        }
        artSearchViewModel.getArtCollection("sunflower")
        advanceUntilIdle()

        assertThat(expectedStates.map { it.javaClass }).containsAtLeast(
            StartLoading::class.java,
            EndLoading::class.java,
            Error::class.java,
        )
        job.cancel()
    }
}
