package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository

interface GetDogBreedImagesUseCase {
    suspend operator fun invoke(breedId: Int): List<String>
}

internal class GetDogBreedImagesUseCaseImpl(
    private val dogRepository: DogRepository
) : GetDogBreedImagesUseCase {

    override suspend fun invoke(breedId: Int): List<String> =
        dogRepository.dogBreedImages(breedId = breedId)
}
