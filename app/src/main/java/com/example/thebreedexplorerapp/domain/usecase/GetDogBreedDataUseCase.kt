package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository
import kotlinx.coroutines.flow.Flow

interface GetDogBreedDataUseCase {
    operator fun invoke(breedId: Int): Flow<Unit> // todo type
}

internal class GetDogBreedDataUseCaseImpl(
    private val dogRepository: DogRepository
) : GetDogBreedDataUseCase {

    override fun invoke(breedId: Int): Flow<Unit> = dogRepository.dogBreed(breedId = breedId)
}
