package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository

interface ToggleDogBreedAsFavoriteUseCase {
    suspend operator fun invoke(breedId: Int)
}

internal class ToggleDogBreedAsFavoriteUseCaseImpl(
    private val dogRepository: DogRepository
) : ToggleDogBreedAsFavoriteUseCase {

    override suspend operator fun invoke(breedId: Int) = dogRepository.toggleDogBreedAsFavorite(breedId)
}
