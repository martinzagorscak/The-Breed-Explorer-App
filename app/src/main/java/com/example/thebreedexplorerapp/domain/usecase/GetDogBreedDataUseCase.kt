package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository
import com.example.thebreedexplorerapp.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface GetDogBreedDataUseCase {
    operator fun invoke(breedId: Int): Flow<DogBreed?>
}

internal class GetDogBreedDataUseCaseImpl(
    private val dogRepository: DogRepository
) : GetDogBreedDataUseCase {

    override fun invoke(breedId: Int): Flow<DogBreed?> = dogRepository.dogBreed(breedId = breedId)
}
