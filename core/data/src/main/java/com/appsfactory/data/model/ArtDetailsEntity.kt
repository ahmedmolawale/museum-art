package com.appsfactory.data.model

data class ArtDetailsEntity(
    val title: String,
    val objectName: String,
    val artistDisplayName: String,
    val artistDisplayBio: String,
    val primaryImage: String,
    val additionalImages: List<String>
)
