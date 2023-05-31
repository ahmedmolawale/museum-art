package com.appsfactory.data.mapper

import com.appsfactory.data.model.ArtDetailsEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ArtDetailsEntityMapperTest {

    private val artDetailsEntityMapper = ArtDetailsEntityMapper()

    @Test
    fun `check that mapFromModel returns correct data`() {
        val artDetailsEntity = ArtDetailsEntity(
            title = "abc",
            artistDisplayName = "a",
            artistDisplayBio = "abc",
            primaryImage = "https://img.jpg",
            additionalImages = emptyList(),
            objectName = "abcd",
        )
        val placeEntity = artDetailsEntityMapper.mapToDomain(artDetailsEntity)
        assertThat(placeEntity.title).isEqualTo("abc")
        assertThat(placeEntity.displayName).isEqualTo("a")
        assertThat(placeEntity.displayBio).isEqualTo("abc")
        assertThat(placeEntity.primaryImage).isEqualTo("https://img.jpg")
        assertThat(placeEntity.additionalImages).isEmpty()
        assertThat(placeEntity.headline).isEqualTo("abcd")
    }
}
