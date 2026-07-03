package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository
import kotlinx.coroutines.flow.Flow

interface GetAllDogBreedsUseCase {
    operator fun invoke(): Flow<List<Unit>> // todo type
}

internal class GetAllDogBreedsUseCaseImpl(
    private val dogRepository: DogRepository
) : GetAllDogBreedsUseCase {

    override fun invoke(): Flow<List<Unit>> = dogRepository.allDogBreeds()
}
