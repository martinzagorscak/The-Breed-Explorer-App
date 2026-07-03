package com.example.thebreedexplorerapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiDogBreedsResponse(
    @SerialName("message") val breedMap: Map<String, List<String>>,
    @SerialName("status") val status: String
)