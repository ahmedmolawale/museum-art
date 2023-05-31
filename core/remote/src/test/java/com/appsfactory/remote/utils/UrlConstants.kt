package com.appsfactory.remote.utils

import com.appsfactory.data.model.ArtIdEntity

internal const val OBJECT_ID = 1L
internal val ART_OBJECT_ID = ArtIdEntity(OBJECT_ID)
internal const val SEARCH_TERM = "aza"
internal const val ART_SEARCH_REQUEST_PATH: String = "/search?q=$SEARCH_TERM"
internal const val ART_DETAILS_REQUEST_PATH: String = "/objects/$OBJECT_ID"
