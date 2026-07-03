package com.example.thebreedexplorerapp.ui.model

typealias ImageUrls = List<String>

data class PresentableDogBreedGallery(
    val breed: PresentableDogBreed,
    val imageUrls: ImageUrls,
) {
    companion object {
        val INITIAL = PresentableDogBreedGallery(
            breed = PresentableDogBreed(
                id = -1,
                name = "",
                isFavorite = false,
            ),
            imageUrls = emptyList(),
        )
    }
}
