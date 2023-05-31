package com.appsfactory.museum.fakes

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.domain.model.ArtDetails
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtDetailsRepository
import com.appsfactory.museum.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeArtDetailsRepository : ArtDetailsRepository {

    private var artDetailsFlow: Flow<Result<ArtDetails>> =
        flowOf(
            Result.Success(
                ArtDetails(
                    title = "abc",
                    displayBio = "Zeus",
                    displayName = "Kafka",
                    additionalImages = emptyList(),
                    primaryImage = "https://img.jpg",
                    headline = "",
                ),
            ),
        )

    var artDetailsResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            artDetailsFlow = makeResponse(value)
        }

    private fun makeResponse(type: ResponseType): Flow<Result<ArtDetails>> {
        return when (type) {
            ResponseType.DATA -> artDetailsFlow
            else -> {
                flowOf(Result.Error(Failure.ServerError))
            }
        }
    }

    override fun getArtDetails(artId: ArtId): Flow<Result<ArtDetails>> {
        return artDetailsFlow
    }
}
