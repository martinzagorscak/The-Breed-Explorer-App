package com.example.thebreedexplorerapp.data.repository

import com.example.thebreedexplorerapp.data.api.DogApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface DogRepository {
    fun allDogBreeds(): Flow<List<Unit>> // todo type
    fun dogBreed(breedId: Int): Flow<Unit> // todo type
    suspend fun dogBreedImages(breedId: Int): List<Unit> // todo type
    suspend fun toggleDogBreedAsFavorite(breedId: Int)
}

internal class DogRepositoryImpl(
    private val dogApi: DogApi,
    scope: CoroutineScope,
) : DogRepository {

    init {
        scope.launch {
            allDogBreedsPublisher.update {
                // todo map to domain model
                dogApi.getAllBreeds()
            }
        }
    }

    private val allDogBreedsPublisher = MutableStateFlow<List<Unit>>(emptyList()) // todo type

    override fun allDogBreeds(): Flow<List<Unit>> = allDogBreedsPublisher

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun dogBreed(breedId: Int): Flow<Unit> = allDogBreedsPublisher.mapLatest {
        // todo impl
        it.firstOrNull { true }
    }

    override suspend fun dogBreedImages(breedId: Int): List<Unit> {
        val breedKeyword = "breedKeyword" // todo get from all dog breeds
        return dogApi.getBreedImages(breedKeyword = breedKeyword)
    }

    override suspend fun toggleDogBreedAsFavorite(breedId: Int) {
        TODO("Not yet implemented")
    }
}
