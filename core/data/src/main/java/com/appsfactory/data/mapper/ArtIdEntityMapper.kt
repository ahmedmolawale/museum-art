package com.appsfactory.data.mapper

import com.appsfactory.data.model.ArtIdEntity
import com.appsfactory.domain.model.ArtId
import javax.inject.Inject

internal class ArtIdEntityMapper @Inject constructor() :
    EntityMapper<ArtIdEntity, ArtId> {
    override fun mapToDomain(entity: ArtIdEntity): ArtId {
        return ArtId(
            value = entity.value
        )
    }
}