package com.appsfactory.domain.model

data class ArtDetails(
    val title: String,
    val headline: String,
    val credit: String,
    val department: String,
    val primaryImage: String,
    val additionalImages: List<String>,
)
