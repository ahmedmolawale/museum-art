package com.appsfactory.data.repositoryimpl

import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtDetailsRemote
import com.appsfactory.data.mapper.ArtDetailsEntityMapper
import com.appsfactory.domain.model.ArtDetails
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class ArtDetailsRepositoryImpl @Inject constructor(
    private val artDetailsRemote: ArtDetailsRemote,
    private val artDetailsEntityMapper: ArtDetailsEntityMapper
) : ArtDetailsRepository {

    override fun getArtDetails(artId: ArtId): Flow<Result<ArtDetails>> {
        return flow {
            when (val artDetails = artDetailsRemote.getArtDetails(artId)) {
                is Result.Success -> {
                    emit(Result.Success(artDetailsEntityMapper.mapToDomain(artDetails.data)))
                }

                is Result.Error -> {
                    emit(Result.Error(artDetails.failure))
                }
            }
        }
    }

}