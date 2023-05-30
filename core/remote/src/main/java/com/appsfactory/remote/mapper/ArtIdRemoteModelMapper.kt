package com.appsfactory.remote.mapper

import com.appsfactory.data.model.ArtIdEntity
import javax.inject.Inject

internal class ArtIdRemoteModelMapper @Inject constructor() :
    RemoteModelMapper<Long, ArtIdEntity> {
    override fun mapFromModel(model: Long): ArtIdEntity {
        return ArtIdEntity(
            model
        )
    }
}