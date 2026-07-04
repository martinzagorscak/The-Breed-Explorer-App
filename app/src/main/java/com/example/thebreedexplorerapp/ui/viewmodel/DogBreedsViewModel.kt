package com.example.thebreedexplorerapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebreedexplorerapp.domain.usecase.GetAllDogBreedsUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetFavoriteDogBreedIdsUseCase
import com.example.thebreedexplorerapp.domain.usecase.RefreshAllDogBreedsUseCase
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCase
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreed
import com.example.thebreedexplorerapp.ui.model.toPresentableDogBreed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

const val EMPTY = ""
private const val SEARCH_QUERY_DEBOUNCE = 300

sealed class DogBreedsViewState {
    data object Loading : DogBreedsViewState()
    data class Loaded(val dogBreeds: List<PresentableDogBreed>) : DogBreedsViewState()
    data object Empty : DogBreedsViewState()
    data object Error : DogBreedsViewState()
}

abstract class DogBreedsViewModel : ViewModel() {
    abstract fun searchQueryViewState(): Flow<String>
    abstract fun dogBreedsViewState(): Flow<DogBreedsViewState>
    abstract fun onSearchQueryChanged(query: String)
    abstract fun clearSearchQuery()
    abstract fun toggleDogBreedAsFavorite(breedId: Int)
    abstract fun refetchAllDogBreeds()
}

internal class DogBreedsViewModelImpl(
    getAllDogBreedsUseCase: GetAllDogBreedsUseCase,
    getFavoriteDogBreedIdsUseCase: GetFavoriteDogBreedIdsUseCase,
    private val toggleDogBreedAsFavoriteUseCase: ToggleDogBreedAsFavoriteUseCase,
    private val refreshAllDogBreedsUseCase: RefreshAllDogBreedsUseCase,
) : DogBreedsViewModel() {

    private val searchQuery = MutableStateFlow(EMPTY)
    private val dogBreedsViewState = combine(
        getAllDogBreedsUseCase(),
        getFavoriteDogBreedIdsUseCase(),
        searchQuery.debounce(SEARCH_QUERY_DEBOUNCE.milliseconds),
    ) { allDogBreeds, favoriteDogBreedIds, searchQuery ->
        val filteredDogBreeds = allDogBreeds
            .filter { it.name.startsWith(prefix = searchQuery, ignoreCase = true) }
            .map { it.toPresentableDogBreed(isFavorite = favoriteDogBreedIds.contains(it.id)) }

        if (filteredDogBreeds.isNotEmpty()) {
            DogBreedsViewState.Loaded(filteredDogBreeds)
        } else if(searchQuery.isNotBlank() && filteredDogBreeds.isEmpty()) {
            DogBreedsViewState.Empty
        } else {
            DogBreedsViewState.Loading
        }
    }
        .catch {
            Log.e("DogBreedsVM", it.message ?: "Error occurred in the view state")
            emit(DogBreedsViewState.Error)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DogBreedsViewState.Loading,
        )

    override fun searchQueryViewState(): Flow<String> = searchQuery

    override fun dogBreedsViewState(): Flow<DogBreedsViewState> = dogBreedsViewState

    override fun onSearchQueryChanged(query: String) = searchQuery.update { query }

    override fun clearSearchQuery() = searchQuery.update { EMPTY }

    override fun toggleDogBreedAsFavorite(breedId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDogBreedAsFavoriteUseCase(breedId)
        }
    }

    override fun refetchAllDogBreeds() {
        viewModelScope.launch(Dispatchers.Default) {
            refreshAllDogBreedsUseCase()
        }
    }
}
