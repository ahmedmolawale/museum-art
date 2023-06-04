package com.appsfactory.museum.features.artdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.appsfactory.common.Result
import com.appsfactory.domain.model.ArtDetails
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtDetailsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtDetailsViewModel @AssistedInject constructor(
    private val artDetailsRepository: ArtDetailsRepository,
    @Assisted private val artId: Long,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiStateModel>(UiStateModel.Default)
    val uiState: StateFlow<UiStateModel> = _uiState.asStateFlow()

    init {
        getArtDetails(ArtId(artId))
    }

    private fun getArtDetails(artId: ArtId) {
        _uiState.value = UiStateModel.StartLoading
        viewModelScope.launch {
            artDetailsRepository.getArtDetails(artId).collect {
                _uiState.value = UiStateModel.EndLoading
                when (it) {
                    is Result.Success -> {
                        val detailsItems = mutableListOf<ArtDetailsItem>()
                        detailsItems.add(
                            ArtDetailsItem.ArticleOverview(
                                ArtDetailsOverview(
                                    it.data.credit,
                                    it.data.title,
                                ),
                            ),
                        )
                        if (it.data.additionalImages.isNotEmpty()) {
                            detailsItems.add(ArtDetailsItem.Header)
                            it.data.additionalImages.forEach { image ->
                                detailsItems.add(ArtDetailsItem.AdditionalImage(image))
                            }
                        }
                        _uiState.value = UiStateModel.ArtDetailsSuccess(
                            it.data,
                            detailsItems,
                        )
                    }

                    is Result.Error -> {
                        _uiState.value = UiStateModel.Error
                    }
                }
            }
        }
    }

    sealed interface UiStateModel {
        object Default : UiStateModel

        object StartLoading : UiStateModel

        object EndLoading : UiStateModel

        data class ArtDetailsSuccess(
            val artDetails: ArtDetails,
            val artDetailsItem: List<ArtDetailsItem>,
        ) : UiStateModel

        object Error : UiStateModel
    }

    @AssistedFactory
    interface ArtDetailsViewModelFactory {
        fun create(artId: Long): ArtDetailsViewModel

        @Suppress("UNCHECKED_CAST")
        companion object {
            fun providesFactory(
                assistedFactory: ArtDetailsViewModelFactory,
                artId: Long,
            ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(artId) as T
                }
            }
        }
    }
}
