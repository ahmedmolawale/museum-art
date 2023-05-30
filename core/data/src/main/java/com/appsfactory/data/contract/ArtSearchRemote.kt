package com.appsfactory.data.contract

import com.appsfactory.common.Result
import com.appsfactory.data.model.ArtIdEntity

interface ArtSearchRemote {

    suspend fun searchArtCollection(searchTerm: String): Result<List<ArtIdEntity>>
}
