package com.appsfactory.domain.repository

import com.appsfactory.common.Result
import com.appsfactory.domain.model.ArtDetails
import com.appsfactory.domain.model.ArtId
import kotlinx.coroutines.flow.Flow

interface ArtDetailsRepository {

    fun getArtDetails(artId: ArtId): Flow<Result<ArtDetails>>
}