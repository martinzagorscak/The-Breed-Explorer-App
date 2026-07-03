package com.example.thebreedexplorerapp.domain.di

import com.example.thebreedexplorerapp.domain.usecase.GetAllDogBreedsUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetAllDogBreedsUseCaseImpl
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedDataUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedDataUseCaseImpl
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedImagesUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedImagesUseCaseImpl
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCase
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<GetAllDogBreedsUseCase> { GetAllDogBreedsUseCaseImpl(dogRepository = get()) }
    single<GetDogBreedDataUseCase> { GetDogBreedDataUseCaseImpl(get()) }
    single<GetDogBreedImagesUseCase> { GetDogBreedImagesUseCaseImpl(dogRepository = get()) }
    single<ToggleDogBreedAsFavoriteUseCase> { ToggleDogBreedAsFavoriteUseCaseImpl(dogRepository = get()) }
}
