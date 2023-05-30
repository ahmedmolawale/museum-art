package com.appsfactory.remote.model

internal data class ArtResponseWrapper(val total: Int?, val objectIDs: List<ArtIdRemote>?)

@JvmInline
internal value class ArtIdRemote(val value: Long)