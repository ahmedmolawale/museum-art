package com.appsfactory.data.contract

import com.appsfactory.data.model.ArtDetailsEntity

interface ArtDetailsRemote {

    suspend fun getArtDetails(id: String): Result<ArtDetailsEntity>
}