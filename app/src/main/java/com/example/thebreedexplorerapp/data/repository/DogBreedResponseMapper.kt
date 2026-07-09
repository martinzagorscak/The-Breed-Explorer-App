package com.example.thebreedexplorerapp.data.repository

import com.example.thebreedexplorerapp.data.model.ApiDogBreedsResponse
import com.example.thebreedexplorerapp.domain.model.DogBreed
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

fun ApiDogBreedsResponse.toDogBreeds(): List<DogBreed> {
    val breedList = mutableListOf<DogBreed>()
    var id = 0

    for ((mainBreed, subBreeds) in breedMap) {
        if (subBreeds.isEmpty()) {
            val dogBreed = DogBreed(
                id = id,
                name = mainBreed.capitalize(),
                keyword = mainBreed.lowercase(),
            )
            breedList.add(dogBreed)
            id++
        } else {
            for (subBreed in subBreeds) {
                val dogSubBreed = DogBreed(
                    id = id,
                    name = "${mainBreed.capitalize()} ${subBreed.capitalize()}",
                    keyword = "${mainBreed.lowercase()}/${subBreed.lowercase()}",
                )
                breedList.add(dogSubBreed)
                id++
            }
        }
    }

    return breedList
}
