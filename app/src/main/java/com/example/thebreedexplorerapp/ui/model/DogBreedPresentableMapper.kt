package com.example.thebreedexplorerapp.ui.model

import com.example.thebreedexplorerapp.domain.model.DogBreed

fun DogBreed.toPresentableDogBreed(isFavorite: Boolean) = PresentableDogBreed(
    id = id,
    name = name,
    isFavorite = isFavorite,
)
