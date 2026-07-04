package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository

interface RefreshAllDogBreedsUseCase {
    suspend operator fun invoke()
}

internal class RefreshAllDogBreedsUseCaseImpl(
    private val dogRepository: DogRepository,
) : RefreshAllDogBreedsUseCase {

    override suspend fun invoke() = dogRepository.refreshAllDogBreeds()
}
