package com.appsfactory.museum.fakes

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtSearchRepository
import com.appsfactory.museum.ResponseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeArtSearchRepository : ArtSearchRepository {

    private var artsFlow: Flow<Result<List<ArtId>>> =
        flowOf(Result.Success(listOf(ArtId(1), ArtId(2))))

    var artListResponseType: ResponseType = ResponseType.DATA
        set(value) {
            field = value
            artsFlow = makeResponse(value)
        }

    private fun makeResponse(type: ResponseType): Flow<Result<List<ArtId>>> {
        return when (type) {
            ResponseType.DATA -> artsFlow
            ResponseType.EMPTY_DATA -> flowOf(Result.Success(listOf()))
            ResponseType.ERROR -> {
                flowOf(Result.Error(Failure.ServerError))
            }
        }
    }

    override fun searchArtCollection(searchTerm: String): Flow<Result<List<ArtId>>> {
        return artsFlow
    }
}
