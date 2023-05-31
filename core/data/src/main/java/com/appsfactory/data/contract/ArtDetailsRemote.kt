package com.appsfactory.data.contract

import com.appsfactory.common.Result
import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.data.model.ArtIdEntity

interface ArtDetailsRemote {

    suspend fun getArtDetails(id: ArtIdEntity): Result<ArtDetailsEntity>
}
