package com.example.thebreedexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebreedexplorerapp.domain.usecase.GetAllDogBreedsUseCase
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCase
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val EMPTY = ""

abstract class DogBreedsViewModel : ViewModel() {
    abstract fun searchQueryViewState(): Flow<String>
    abstract fun allDogBreedsViewState(): Flow<List<PresentableDogBreed>>
    abstract fun onSearchQueryChanged(query: String)
    abstract fun clearSearchQuery()
    abstract fun toggleDogBreedAsFavorite(breedId: Int)
}

internal class DogBreedsViewModelImpl(
    private val getAllDogBreedsUseCase: GetAllDogBreedsUseCase,
    private val toggleDogBreedAsFavoriteUseCase: ToggleDogBreedAsFavoriteUseCase,
) : DogBreedsViewModel() {

    private val searchQuery = MutableStateFlow(EMPTY)

    override fun searchQueryViewState(): Flow<String> = searchQuery

    override fun allDogBreedsViewState(): Flow<List<PresentableDogBreed>> = combine(
        getAllDogBreedsUseCase(),
        searchQuery,
    ) { allDogBreeds, searchQuery ->
        // TODO impl filtering and real data
        listOf(
            PresentableDogBreed(id = 1, name = "Sausage Dog", isFavorite = false),
            PresentableDogBreed(id = 2, name = "Golden Retriever", isFavorite = true),
            PresentableDogBreed(id = 3, name = "German Shepherd", isFavorite = false),
        )
    }

    override fun onSearchQueryChanged(query: String) = searchQuery.update { query }

    override fun clearSearchQuery() = searchQuery.update { EMPTY }

    override fun toggleDogBreedAsFavorite(breedId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDogBreedAsFavoriteUseCase(breedId)
        }
    }
}
