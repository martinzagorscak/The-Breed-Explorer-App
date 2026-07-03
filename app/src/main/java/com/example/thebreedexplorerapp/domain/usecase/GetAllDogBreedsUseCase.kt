package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository
import com.example.thebreedexplorerapp.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface GetAllDogBreedsUseCase {
    operator fun invoke(): Flow<List<DogBreed>>
}

internal class GetAllDogBreedsUseCaseImpl(
    private val dogRepository: DogRepository
) : GetAllDogBreedsUseCase {

    override fun invoke(): Flow<List<DogBreed>> = dogRepository.allDogBreeds()
}
