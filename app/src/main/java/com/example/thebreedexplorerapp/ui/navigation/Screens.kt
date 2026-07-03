package com.example.thebreedexplorerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreed
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreedGallery
import com.example.thebreedexplorerapp.ui.screens.DogBreedDetailsScreen
import com.example.thebreedexplorerapp.ui.screens.DogBreedsDetailsScreenCallbacks
import com.example.thebreedexplorerapp.ui.screens.DogBreedsScreen
import com.example.thebreedexplorerapp.ui.screens.DogBreedsScreenCallbacks
import kotlinx.serialization.Serializable

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
            // todo impl with real data - include viewmodel
            val emptyQuery = ""
            var dogBreedQuery by remember { mutableStateOf(emptyQuery) }

            DogBreedsScreen(
                dogBreedQuery = dogBreedQuery,
                dogBreeds = listOf(
                    PresentableDogBreed(id = 1, name = "Sausage Dog", isFavorite = false),
                    PresentableDogBreed(id = 2, name = "Golden Retriever", isFavorite = true),
                    PresentableDogBreed(id = 3, name = "German Shepherd", isFavorite = false),
                ),
                callbacks = remember {
                    DogBreedsScreenCallbacks(
                        onQueryChange = { dogBreedQuery = it },
                        onQueryClear = { dogBreedQuery = emptyQuery },
                        onDogBreedClick = { dogBreedId ->
                            navController.navigate(route = Screen.DogBreedDetailsScreen(dogBreedId))
                        },
                        onAddDogBreedToFavoriteClick = {},
                    )
                }
            )
        }
        composable<Screen.DogBreedDetailsScreen> { backStackEntry ->
            // todo impl with real data - include viewmodel
            val screen: Screen.DogBreedDetailsScreen = backStackEntry.toRoute()
            val dogBreedId = screen.breedId

            val mockedList = listOf(
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10",
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10",
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10",
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10",
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10",
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10",
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10",
                "https://cricksydog.hr/wp-content/uploads/2022/02/dachshund_PNG15.png",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTP9lgK0is2vPr-PLfLMlxsQPxjd_ela1ZF9joRPBZ0ChR43HdwaToMn1A&s=10"
            )

            DogBreedDetailsScreen(
                dogBreedGallery = PresentableDogBreedGallery(
                    breed = PresentableDogBreed(id = dogBreedId, name = "Sausage Dog", isFavorite = false),
                    imageUrls = mockedList,
                ),
                callbacks = remember {
                    DogBreedsDetailsScreenCallbacks(
                        onBackClick = { navController.popBackStack() },
                        onAddToFavoritesClick = {},
                    )
                }
            )
        }
    }
}
