package com.appsfactory.data.mapper

import com.appsfactory.data.model.ArtDetailsEntity
import com.appsfactory.domain.model.ArtDetails
import javax.inject.Inject

internal class ArtDetailsEntityMapper @Inject constructor() :
    EntityMapper<ArtDetailsEntity, ArtDetails> {
    override fun mapToDomain(entity: ArtDetailsEntity): ArtDetails {
        return ArtDetails(
            title = entity.title,
            headline = entity.objectName,
            displayName = entity.artistDisplayName,
            displayBio = entity.artistDisplayBio,
            primaryImage = entity.primaryImage,
            additionalImages = entity.additionalImages,
        )
    }
}
