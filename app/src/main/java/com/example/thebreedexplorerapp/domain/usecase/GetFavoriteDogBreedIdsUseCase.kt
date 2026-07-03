package com.example.thebreedexplorerapp.domain.usecase

import com.example.thebreedexplorerapp.data.repository.DogRepository
import kotlinx.coroutines.flow.Flow

interface GetFavoriteDogBreedIdsUseCase {
    operator fun invoke(): Flow<List<Int>>
}

internal class GetFavoriteDogBreedIdsUseCaseImpl(
    private val dogRepository: DogRepository
) : GetFavoriteDogBreedIdsUseCase {

    override fun invoke(): Flow<List<Int>> = dogRepository.favoriteDogBreeds()
}
