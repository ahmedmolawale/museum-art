package com.appsfactory.data.contract

import com.appsfactory.common.Result
import com.appsfactory.data.model.ArtDetailsEntity

interface ArtDetailsRemote {

    suspend fun getArtDetails(id: Long): Result<ArtDetailsEntity>
}