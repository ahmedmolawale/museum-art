package com.appsfactory.museum.features.artdetails

sealed interface ArtDetailsItem {
    class ArticleOverview(val artDetailsOverview: ArtDetailsOverview) : ArtDetailsItem
    object Header : ArtDetailsItem
    class AdditionalImage(val image: String) : ArtDetailsItem
}
