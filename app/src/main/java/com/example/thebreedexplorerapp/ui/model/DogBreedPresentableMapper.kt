package com.example.thebreedexplorerapp.ui.model

import com.example.thebreedexplorerapp.domain.model.DogBreed

fun DogBreed.toPresentableDogBreed() = PresentableDogBreed(
    id = id,
    name = name,
    isFavorite = isFavorite,
)
