package com.appsfactory.data.model

data class ArtDetailsEntity(
    val title: String,
    val objectName: String,
    val creditLine: String,
    val department: String,
    val primaryImage: String,
    val additionalImages: List<String>,
)
