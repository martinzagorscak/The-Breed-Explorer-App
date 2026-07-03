package com.example.thebreedexplorerapp.data.api

import com.example.thebreedexplorerapp.data.model.ApiDogBreedGalleryResponse
import com.example.thebreedexplorerapp.data.model.ApiDogBreedsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface DogApi {
    suspend fun getAllBreeds(): ApiDogBreedsResponse
    suspend fun getBreedImages(breedKeyword: String): ApiDogBreedGalleryResponse
}

internal class DogApiImpl(private val client: HttpClient) : DogApi {

    override suspend fun getAllBreeds(): ApiDogBreedsResponse =
        client.get("https://dog.ceo/api/breeds/list/all").body<ApiDogBreedsResponse>()

    override suspend fun getBreedImages(breedKeyword: String): ApiDogBreedGalleryResponse =
        client.get("https://dog.ceo/api/breed/$breedKeyword/images/random/20").body<ApiDogBreedGalleryResponse>()
}
