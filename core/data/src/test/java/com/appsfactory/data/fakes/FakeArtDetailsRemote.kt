package com.appsfactory.data.fakes

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtDetailsRemote
import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.data.util.ResponseType
import com.appsfactory.domain.model.ArtId

internal val artDetailsEntity = ArtDetailsEntity(
    title = "abc",
    artistDisplayName = "ola",
    artistDisplayBio = "",
    objectName = "abcd",
    primaryImage = "https://img.png",
    additionalImages = emptyList()
)

internal class FakeArtDetailsRemote : ArtDetailsRemote {


    private var artDetailsResult: Result<ArtDetailsEntity> =
        Result.Success(artDetailsEntity)

    var artDetailsResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            artDetailsResult = makeResponse(value)
        }

    private fun makeResponse(type: ResponseType): Result<ArtDetailsEntity> {
        return when (type) {
            ResponseType.DATA -> artDetailsResult
            else -> Result.Error(Failure.ServerError)
        }
    }

    override suspend fun getArtDetails(id: ArtId): Result<ArtDetailsEntity> {
        return artDetailsResult
    }
}