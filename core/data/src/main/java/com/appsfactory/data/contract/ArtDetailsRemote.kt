package com.appsfactory.data.contract

import com.appsfactory.common.Result
import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.domain.model.ArtId

interface ArtDetailsRemote {

    suspend fun getArtDetails(id: ArtId): Result<ArtDetailsEntity>
}
