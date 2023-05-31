package com.appsfactory.museum.features.artdetails

import com.appsfactory.domain.model.ArtId
import com.appsfactory.museum.fakes.FakeArtDetailsRepository
import com.appsfactory.museum.features.artdetails.ArtDetailsViewModel.UiStateModel
import com.appsfactory.museum.features.artdetails.ArtDetailsViewModel.UiStateModel.ArtDetailsSuccess
import com.appsfactory.museum.features.artdetails.ArtDetailsViewModel.UiStateModel.EndLoading
import com.appsfactory.museum.features.artdetails.ArtDetailsViewModel.UiStateModel.Error
import com.appsfactory.museum.features.artdetails.ArtDetailsViewModel.UiStateModel.StartLoading
import com.appsfactory.museum.MainDispatcherRule
import com.appsfactory.museum.ResponseType
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
class ArtDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private lateinit var fakeArtDetailsRepository: FakeArtDetailsRepository
    private lateinit var artDetailsViewModel: ArtDetailsViewModel

    @Before
    fun setup() {
        fakeArtDetailsRepository = FakeArtDetailsRepository()
        artDetailsViewModel = ArtDetailsViewModel(
            fakeArtDetailsRepository,
            ArtId(1),
        )
    }

    @Test
    fun `check that init returns correct data`() = runTest {
        fakeArtDetailsRepository.artDetailsResponseType = ResponseType.DATA

        val expectedStates = mutableListOf<UiStateModel>()
        val job = launch(UnconfinedTestDispatcher()) {
            artDetailsViewModel.uiState.toList(expectedStates)
        }
        advanceUntilIdle()

        assertThat(expectedStates.map { it.javaClass }).containsAtLeast(
            StartLoading::class.java,
            EndLoading::class.java,
            ArtDetailsSuccess::class.java,
        )
        job.cancel()
    }

    @Test
    fun `check that init returns error`() = runTest {
        fakeArtDetailsRepository.artDetailsResponseType = ResponseType.ERROR

        val expectedStates = mutableListOf<UiStateModel>()
        val job = launch(UnconfinedTestDispatcher()) {
            artDetailsViewModel.uiState.toList(expectedStates)
        }
        advanceUntilIdle()

        assertThat(expectedStates.map { it.javaClass }).containsAtLeast(
            StartLoading::class.java,
            EndLoading::class.java,
            Error::class.java,
        )
        job.cancel()
    }
}
