package com.appsfactory.remote.mapper

import com.appsfactory.data.model.ArtIdEntity
import com.appsfactory.remote.model.ArtIdRemote
import javax.inject.Inject

internal class ArtIdRemoteModelMapper @Inject constructor() :
    RemoteModelMapper<ArtIdRemote, ArtIdEntity> {
    override fun mapFromModel(model: ArtIdRemote): ArtIdEntity {
        return ArtIdEntity(
            model.value
        )
    }
}