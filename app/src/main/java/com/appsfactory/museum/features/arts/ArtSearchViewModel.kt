package com.appsfactory.museum.features.arts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsfactory.common.Result
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtSearchViewModel @Inject constructor(
    private val artSearchRepository: ArtSearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiStateModel>(UiStateModel.Default)
    val uiState: StateFlow<UiStateModel> = _uiState.asStateFlow()

    fun getArtCollection(searchQuery: String) {
        if (searchQuery.length < 3) return
        _uiState.value = UiStateModel.StartLoading
        viewModelScope.launch {
            artSearchRepository.searchArtCollection(searchQuery).collect {
                _uiState.value = UiStateModel.EndLoading
                when (it) {
                    is Result.Success -> {
                        _uiState.value = if (it.data.isEmpty()) {
                            UiStateModel.EmptyArtCollection
                        } else {
                            UiStateModel.ArtCollection(it.data)
                        }
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

        data class ArtCollection(
            val artCollection: List<ArtId>,
        ) : UiStateModel

        object EmptyArtCollection : UiStateModel

        object Error : UiStateModel
    }
}
