package com.example.thebreedexplorerapp.ui.viewmodel

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<DogBreedsViewModel> {
        DogBreedsViewModelImpl(
            getAllDogBreedsUseCase = get(),
            toggleDogBreedAsFavoriteUseCase = get(),
        )
    }

    viewModel<DogBreedGalleryViewModel> {(dogBreedId: Int) ->
        DogBreedGalleryViewModelImpl(
            dogBreedId = dogBreedId,
            getDogBreedDataUseCase = get(),
            getDogBreedImagesUseCase = get(),
            toggleDogBreedAsFavoriteUseCase = get(),
        )
    }
}
