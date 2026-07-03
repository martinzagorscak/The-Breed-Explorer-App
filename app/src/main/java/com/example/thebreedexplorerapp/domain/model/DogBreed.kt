package com.example.thebreedexplorerapp.domain.model

data class DogBreed(
    val id: Int,
    val name: String,
    val keyword: String, // "breed-subBreed" for searching purposes
)
