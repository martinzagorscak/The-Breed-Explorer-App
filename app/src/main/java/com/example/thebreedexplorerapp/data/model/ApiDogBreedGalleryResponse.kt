package com.example.thebreedexplorerapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiDogBreedGalleryResponse(
    @SerialName("message") val imageUrls: List<String>,
    @SerialName("status") val status: String
)
