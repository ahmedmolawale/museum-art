package com.appsfactory.domain.model

data class ArtDetails(
    val title: String,
    val headline: String,
    val displayName: String,
    val displayBio: String,
    val primaryImage: String,
    val additionalImages: List<String>
)