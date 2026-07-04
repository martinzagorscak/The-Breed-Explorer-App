package com.example.thebreedexplorerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.thebreedexplorerapp.ui.screens.DogBreedDetailsScreen
import com.example.thebreedexplorerapp.ui.screens.DogBreedsDetailsScreenCallbacks
import com.example.thebreedexplorerapp.ui.screens.DogBreedsScreen
import com.example.thebreedexplorerapp.ui.screens.DogBreedsScreenCallbacks
import com.example.thebreedexplorerapp.ui.viewmodel.DogBreedGalleryViewModel
import com.example.thebreedexplorerapp.ui.viewmodel.DogBreedGalleryViewState
import com.example.thebreedexplorerapp.ui.viewmodel.DogBreedsViewModel
import com.example.thebreedexplorerapp.ui.viewmodel.DogBreedsViewState
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
sealed class Screen {

    @Serializable
    data object DogBreedsScreen : Screen()

    @Serializable
    data class DogBreedDetailsScreen(val breedId: Int) : Screen()
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DogBreedsScreen,
    ) {
        composable<Screen.DogBreedsScreen> {
            val viewModel = koinViewModel<DogBreedsViewModel>()
            val searchQuery = viewModel.searchQueryViewState().collectAsState(initial = "").value
            val dogBreedsViewState = viewModel.dogBreedsViewState().collectAsState(initial = DogBreedsViewState.Loading).value

            DogBreedsScreen(
                dogBreedQuery = searchQuery,
                dogBreedsViewState = dogBreedsViewState,
                callbacks = remember {
                    DogBreedsScreenCallbacks(
                        onQueryChange = viewModel::onSearchQueryChanged,
                        onQueryClear = viewModel::clearSearchQuery,
                        onDogBreedClick = { dogBreedId ->
                            navController.navigate(route = Screen.DogBreedDetailsScreen(dogBreedId))
                        },
                        onAddDogBreedToFavoriteClick = viewModel::toggleDogBreedAsFavorite,
                        onTryAgainClick = viewModel::refetchAllDogBreeds,
                    )
                }
            )
        }
        composable<Screen.DogBreedDetailsScreen> { backStackEntry ->
            val screen: Screen.DogBreedDetailsScreen = backStackEntry.toRoute()
            val dogBreedId = screen.breedId
            val viewModel = koinViewModel<DogBreedGalleryViewModel>(parameters = { parametersOf(dogBreedId) })
            val dogBreedGalleryViewState = viewModel.dogBreedGalleryViewState().collectAsState(initial = DogBreedGalleryViewState.Loading).value

            DogBreedDetailsScreen(
                dogBreedGalleryViewState = dogBreedGalleryViewState,
                callbacks = remember {
                    DogBreedsDetailsScreenCallbacks(
                        onBackClick = { navController.popBackStack() },
                        onToggleDogBreedAsFavorite = viewModel::toggleDogBreedAsFavorite,
                    )
                }
            )
        }
    }
}
