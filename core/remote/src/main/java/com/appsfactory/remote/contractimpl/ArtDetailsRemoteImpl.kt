package com.appsfactory.remote.contractimpl

import com.appsfactory.common.Failure
import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtDetailsRemote
import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.remote.apiservice.ArtApiService
import com.appsfactory.remote.mapper.ArtDetailsRemoteModelMapper
import javax.inject.Inject


internal class ArtDetailsRemoteImpl @Inject constructor(
    private val artApiService: ArtApiService,
    private val artDetailsRemoteModelMapper: ArtDetailsRemoteModelMapper
) : ArtDetailsRemote {

    override suspend fun getArtDetails(id: Long): Result<ArtDetailsEntity> {
        return try {
            val response = artApiService.getArtDetails(id)
            when (response.isSuccessful) {
                true -> {
                    response.body()?.let {
                        Result.Success(
                            artDetailsRemoteModelMapper.mapFromModel(
                                it
                            )
                        )
                    } ?: Result.Error(Failure.ServerError)
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