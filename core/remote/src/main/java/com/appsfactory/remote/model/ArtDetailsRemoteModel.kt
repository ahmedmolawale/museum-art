package com.appsfactory.remote.model

internal data class ArtDetailsRemoteModel(
    val title: String?,
    val objectName: String?,
    val artistDisplayName: String?,
    val artistDisplayBio: String?,
    val primaryImage: String?,
    val additionalImages: List<String>?,
)
