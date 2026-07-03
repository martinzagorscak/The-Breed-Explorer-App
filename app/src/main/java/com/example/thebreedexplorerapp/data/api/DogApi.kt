package com.example.thebreedexplorerapp.data.api

interface DogApi {
    suspend fun getAllBreeds(): List<Unit> // todo type
    suspend fun getBreedImages(breedKeyword: String): List<Unit> // todo type
}

internal class DogApiImpl : DogApi {

    override suspend fun getAllBreeds(): List<Unit> {
        // todo implement
        return emptyList()
    }

    override suspend fun getBreedImages(breedKeyword: String): List<Unit> {
        // todo implement
        return emptyList()
    }
}
