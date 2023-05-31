package com.appsfactory.museum.features.artdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsfactory.common.Result
import com.appsfactory.domain.model.ArtDetails
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtDetailsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtDetailsViewModel @AssistedInject constructor(
    private val artDetailsRepository: ArtDetailsRepository,
    @Assisted private val artId: ArtId,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiStateModel>(UiStateModel.Default)
    val uiState: StateFlow<UiStateModel> = _uiState.asStateFlow()

    init {
        getArtDetails(artId)
    }

    private fun getArtDetails(artId: ArtId) {
        _uiState.value = UiStateModel.StartLoading
        viewModelScope.launch {
            artDetailsRepository.getArtDetails(artId).collect {
                _uiState.value = UiStateModel.EndLoading
                when (it) {
                    is Result.Success -> {
                        _uiState.value = UiStateModel.ArtDetailsSuccess(it.data)
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
        ) : UiStateModel

        object Error : UiStateModel
    }
}
