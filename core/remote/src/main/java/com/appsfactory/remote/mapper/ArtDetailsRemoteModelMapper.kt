package com.appsfactory.remote.mapper

import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.remote.model.ArtDetailsRemoteModel
import javax.inject.Inject

internal class ArtDetailsRemoteModelMapper @Inject constructor() :
    RemoteModelMapper<ArtDetailsRemoteModel, ArtDetailsEntity> {
    override fun mapFromModel(model: ArtDetailsRemoteModel): ArtDetailsEntity {
        return ArtDetailsEntity(
            title = model.title.orEmpty(),
            primaryImage = model.primaryImage.orEmpty(),
            additionalImages = model.additionalImages ?: emptyList(),
            creditLine = model.creditLine.orEmpty(),
            department = model.department.orEmpty(),
            objectName = model.objectName.orEmpty(),
        )
    }
}
