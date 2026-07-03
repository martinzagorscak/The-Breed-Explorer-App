package com.example.thebreedexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedDataUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedImagesUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetFavoriteDogBreedIdsUseCase
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCase
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreedGallery
import com.example.thebreedexplorerapp.ui.model.toPresentableDogBreed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

abstract class DogBreedGalleryViewModel : ViewModel() {

    abstract fun dogBreedGalleryViewState(): Flow<PresentableDogBreedGallery>
    abstract fun toggleDogBreedAsFavorite()
}

internal class DogBreedGalleryViewModelImpl(
    private val dogBreedId: Int,
    private val getDogBreedDataUseCase: GetDogBreedDataUseCase,
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase,
    private val getFavoriteDogBreedIdsUseCase: GetFavoriteDogBreedIdsUseCase,
    private val toggleDogBreedAsFavoriteUseCase: ToggleDogBreedAsFavoriteUseCase,
) : DogBreedGalleryViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun dogBreedGalleryViewState(): Flow<PresentableDogBreedGallery> =
        combine(
            getDogBreedDataUseCase(breedId = dogBreedId),
            getFavoriteDogBreedIdsUseCase()
        ) { dogBreed, favoriteDogBreedIds ->
            val images = getDogBreedImagesUseCase(breedId = dogBreedId)

            dogBreed?.let {
                PresentableDogBreedGallery(
                    breed = dogBreed.toPresentableDogBreed(isFavorite = favoriteDogBreedIds.contains(dogBreedId)), // TODO replace with real value
                    imageUrls = images,
                )
            } ?: PresentableDogBreedGallery.INITIAL
        }

    override fun toggleDogBreedAsFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDogBreedAsFavoriteUseCase(breedId = dogBreedId)
        }
    }
}
