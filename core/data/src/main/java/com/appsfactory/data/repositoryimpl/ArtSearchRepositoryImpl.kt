package com.appsfactory.data.repositoryimpl

import com.appsfactory.common.Result
import com.appsfactory.data.contract.ArtSearchRemote
import com.appsfactory.data.mapper.ArtIdEntityMapper
import com.appsfactory.domain.model.ArtId
import com.appsfactory.domain.repository.ArtSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class ArtSearchRepositoryImpl @Inject constructor(
    private val artSearchRemote: ArtSearchRemote,
    private val artIdEntityMapper: ArtIdEntityMapper
) : ArtSearchRepository {
    override fun searchArtCollection(searchTerm: String): Flow<Result<List<ArtId>>> {
        return flow {
            when (val places = artSearchRemote.searchArtCollection(searchTerm)) {
                is Result.Success -> {
                    emit(Result.Success(artIdEntityMapper.mapToDomainList(places.data)))
                }

                is Result.Error -> {
                    emit(Result.Error(places.failure))
                }
            }
        }
    }

}