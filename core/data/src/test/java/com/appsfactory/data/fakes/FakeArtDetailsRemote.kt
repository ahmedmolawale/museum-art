package com.appsfactory.data.fakes

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtDetailsRemote
import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.data.model.ArtIdEntity
import com.appsfactory.data.util.ResponseType

internal val artDetailsEntity = ArtDetailsEntity(
    title = "abc",
    creditLine = "ola",
    department = "",
    objectName = "abcd",
    primaryImage = "https://img.png",
    additionalImages = emptyList(),
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

    override suspend fun getArtDetails(id: ArtIdEntity): Result<ArtDetailsEntity> {
        return artDetailsResult
    }
}
