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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class DogBreedGalleryViewState {
    data object Loading : DogBreedGalleryViewState()
    data class Loaded(val gallery: PresentableDogBreedGallery) : DogBreedGalleryViewState()
    data object Error : DogBreedGalleryViewState()
}

abstract class DogBreedGalleryViewModel : ViewModel() {
    abstract fun dogBreedGalleryViewState(): Flow<DogBreedGalleryViewState>
    abstract fun toggleDogBreedAsFavorite()
}

internal class DogBreedGalleryViewModelImpl(
    private val dogBreedId: Int,
    getDogBreedDataUseCase: GetDogBreedDataUseCase,
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase,
    getFavoriteDogBreedIdsUseCase: GetFavoriteDogBreedIdsUseCase,
    private val toggleDogBreedAsFavoriteUseCase: ToggleDogBreedAsFavoriteUseCase,
) : DogBreedGalleryViewModel() {

    private val dogBreedGalleryViewState = combine(
        getDogBreedDataUseCase(breedId = dogBreedId),
        getFavoriteDogBreedIdsUseCase()
    ) { dogBreed, favoriteDogBreedIds ->
        val images = getDogBreedImagesUseCase(breedId = dogBreedId)

        dogBreed?.let {
            DogBreedGalleryViewState.Loaded(
                gallery = PresentableDogBreedGallery(
                    breed = dogBreed.toPresentableDogBreed(isFavorite = favoriteDogBreedIds.contains(dogBreedId)),
                    imageUrls = images,
                )
            )
        } ?: DogBreedGalleryViewState.Error

    }
        .catch { emit(DogBreedGalleryViewState.Error) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DogBreedGalleryViewState.Loading,
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun dogBreedGalleryViewState(): Flow<DogBreedGalleryViewState> = dogBreedGalleryViewState

    override fun toggleDogBreedAsFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDogBreedAsFavoriteUseCase(breedId = dogBreedId)
        }
    }
}
