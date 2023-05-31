package com.appsfactory.data.fakes

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtSearchRemote
import com.appsfactory.data.model.ArtIdEntity
import com.appsfactory.data.util.ResponseType

internal class FakeArtSearchRemote : ArtSearchRemote {

    private var artSearchResult: Result<List<ArtIdEntity>> =
        Result.Success(listOf(ArtIdEntity(1), ArtIdEntity(3)))

    var artSearchResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            artSearchResult = makeResponse(value)
        }

    private fun makeResponse(type: ResponseType): Result<List<ArtIdEntity>> {
        return when (type) {
            ResponseType.DATA -> artSearchResult
            ResponseType.EMPTY_DATA -> Result.Success(emptyList())
            ResponseType.ERROR -> Result.Error(Failure.ServerError)
        }
    }

    override suspend fun searchArtCollection(searchTerm: String): Result<List<ArtIdEntity>> {
        return artSearchResult
    }
}
