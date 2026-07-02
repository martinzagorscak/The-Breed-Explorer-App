package com.example.thebreedexplorerapp.ui.model

typealias ImageUrls = List<String>

data class PresentableDogBreedGallery(
    val breed: PresentableDogBreed,
    val imageUrls: ImageUrls,
)
