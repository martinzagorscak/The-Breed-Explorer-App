package com.example.thebreedexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebreedexplorerapp.domain.usecase.GetAllDogBreedsUseCase
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val EMPTY = ""

abstract class DogBreedsViewModel : ViewModel() {
    abstract fun searchQueryViewState(): Flow<String>
    abstract fun allDogBreedsViewState(): Flow<Unit> // todo type
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

    override fun allDogBreedsViewState(): Flow<Unit> = combine(
        getAllDogBreedsUseCase(),
        searchQuery,
    ) { allDogBreeds, searchQuery ->
        // TODO impl filtering
    }

    override fun onSearchQueryChanged(query: String) = searchQuery.update { query }

    override fun clearSearchQuery() = searchQuery.update { EMPTY }

    override fun toggleDogBreedAsFavorite(breedId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDogBreedAsFavoriteUseCase(breedId)
        }
    }
}
