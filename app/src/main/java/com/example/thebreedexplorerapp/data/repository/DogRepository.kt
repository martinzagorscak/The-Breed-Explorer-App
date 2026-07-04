package com.example.thebreedexplorerapp.data.repository

import android.util.Log
import com.example.thebreedexplorerapp.data.api.DogApi
import com.example.thebreedexplorerapp.domain.model.DogBreed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface DogRepository {
    fun allDogBreeds(): Flow<List<DogBreed>>
    fun dogBreed(breedId: Int): Flow<DogBreed?>
    fun favoriteDogBreeds(): Flow<List<Int>>
    suspend fun dogBreedImages(breedId: Int): List<String>
    suspend fun toggleDogBreedAsFavorite(breedId: Int)
    suspend fun refreshAllDogBreeds()
}

internal class DogRepositoryImpl(
    private val dogApi: DogApi,
    scope: CoroutineScope,
) : DogRepository {

    private val refreshPublisher = MutableSharedFlow<Unit>()
    private val allDogBreedsPublisher = MutableStateFlow<List<DogBreed>>(emptyList())
    private val favoriteDogBreedsPublisher = MutableStateFlow<List<Int>>(emptyList()) // list of dog breed Ids

    init {
        scope.launch {
            refreshPublisher
                .onStart { emit(Unit) }
                .collect {
                    allDogBreedsPublisher.update { dogApi.getAllBreeds().toDogBreeds() }
                }
        }
    }

    override fun allDogBreeds(): Flow<List<DogBreed>> = allDogBreedsPublisher

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun dogBreed(breedId: Int): Flow<DogBreed?> = allDogBreedsPublisher.mapLatest { dogBreeds ->
        dogBreeds.firstOrNull { it.id == breedId }
    }

    override fun favoriteDogBreeds(): Flow<List<Int>> = favoriteDogBreedsPublisher

    override suspend fun dogBreedImages(breedId: Int): List<String> {
        val breedKeyword = allDogBreedsPublisher.value.firstOrNull { it.id == breedId }?.keyword
        return breedKeyword?.let { dogApi.getBreedImages(breedKeyword = it) }?.imageUrls ?: emptyList()
    }

    override suspend fun toggleDogBreedAsFavorite(breedId: Int) {
        with(favoriteDogBreedsPublisher) {
            if (value.contains(breedId)) {
                update { value.filter { it != breedId } }
            } else {
                update { value + breedId }
            }
        }
    }

    override suspend fun refreshAllDogBreeds() = refreshPublisher.emit(Unit)
}
