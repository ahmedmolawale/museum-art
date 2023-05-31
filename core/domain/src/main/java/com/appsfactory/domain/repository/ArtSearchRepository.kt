package com.appsfactory.domain.repository

import com.appsfactory.common.Result
import com.appsfactory.domain.model.ArtId
import kotlinx.coroutines.flow.Flow

interface ArtSearchRepository {

    fun searchArtCollection(searchTerm: String): Flow<Result<List<ArtId>>>
}
