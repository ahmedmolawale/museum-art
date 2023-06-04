package com.appsfactory.remote.model

internal data class ArtDetailsRemoteModel(
    val title: String?,
    val objectName: String?,
    val department: String?,
    val creditLine: String?,
    val primaryImage: String?,
    val additionalImages: List<String>?,
)
