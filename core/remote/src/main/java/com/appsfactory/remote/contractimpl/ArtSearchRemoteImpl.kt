package com.appsfactory.remote.contractimpl

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtSearchRemote
import com.appsfactory.data.model.ArtIdEntity
import com.appsfactory.remote.apiservice.ArtApiService
import com.appsfactory.remote.mapper.ArtIdRemoteModelMapper
import javax.inject.Inject

internal class ArtSearchRemoteImpl @Inject constructor(
    private val artApiService: ArtApiService,
    private val artIdRemoteModelMapper: ArtIdRemoteModelMapper,
) : ArtSearchRemote {
    override suspend fun searchArtCollection(searchTerm: String): Result<List<ArtIdEntity>> {
        return try {
            val response = artApiService.searchArtCollection(searchTerm)
            when (response.isSuccessful) {
                true -> {
                    response.body()?.let {
                        Result.Success(
                            artIdRemoteModelMapper.mapFromModelList(
                                it.objectIDs,
                            ),
                        )
                    } ?: Result.Success(emptyList())
                }

                false -> {
                    Result.Error(Failure.ServerError)
                }
            }
        } catch (e: Throwable) {
            Result.Error(Failure.ServerError)
        }
    }
}
