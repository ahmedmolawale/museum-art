package com.appsfactory.remote.mapper

import com.appsfactory.remote.model.ArtDetailsRemoteModel
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ArtDetailsRemoteModelMapperTest {

    private val artDetailsRemoteModelMapper = ArtDetailsRemoteModelMapper()

    @Test
    fun `check that mapFromModel returns correct data`() {
        val artDetailsRemoteModel = ArtDetailsRemoteModel(
            title = "abc",
            artistDisplayName = null,
            artistDisplayBio = null,
            primaryImage = "https://img.jpg",
            additionalImages = null,
            objectName = "abcd"
        )
        val placeEntity = artDetailsRemoteModelMapper.mapFromModel(artDetailsRemoteModel)
        assertThat(placeEntity.title).isEqualTo("abc")
        assertThat(placeEntity.artistDisplayName).isEmpty()
        assertThat(placeEntity.artistDisplayBio).isEmpty()
        assertThat(placeEntity.primaryImage).isEqualTo("https://img.jpg")
        assertThat(placeEntity.additionalImages).isEmpty()
        assertThat(placeEntity.objectName).isEqualTo("abcd")
    }

}