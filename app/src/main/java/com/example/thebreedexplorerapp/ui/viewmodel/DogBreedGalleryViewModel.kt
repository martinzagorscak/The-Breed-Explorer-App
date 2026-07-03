package com.example.thebreedexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedDataUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedImagesUseCase
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

abstract class DogBreedGalleryViewModel : ViewModel() {

    abstract fun dogBreedGalleryViewState(): Flow<Unit> // todo type
    abstract fun toggleDogBreedAsFavorite()
}

internal class DogBreedGalleryViewModelImpl(
    private val dogBreedId: Int,
    private val getDogBreedDataUseCase: GetDogBreedDataUseCase,
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase,
    private val toggleDogBreedAsFavoriteUseCase: ToggleDogBreedAsFavoriteUseCase,
) : DogBreedGalleryViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun dogBreedGalleryViewState(): Flow<Unit> = getDogBreedDataUseCase(breedId = dogBreedId).mapLatest {
        val images = getDogBreedImagesUseCase(breedId = dogBreedId)
        // todo impl
    }

    override fun toggleDogBreedAsFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDogBreedAsFavoriteUseCase(breedId = dogBreedId)
        }
    }
}
