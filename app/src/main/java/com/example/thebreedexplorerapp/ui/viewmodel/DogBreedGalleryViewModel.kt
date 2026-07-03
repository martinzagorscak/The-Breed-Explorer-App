package com.example.thebreedexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedDataUseCase
import com.example.thebreedexplorerapp.domain.usecase.GetDogBreedImagesUseCase
import com.example.thebreedexplorerapp.domain.usecase.ToggleDogBreedAsFavoriteUseCase
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreed
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreedGallery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

abstract class DogBreedGalleryViewModel : ViewModel() {

    abstract fun dogBreedGalleryViewState(): Flow<PresentableDogBreedGallery>
    abstract fun toggleDogBreedAsFavorite()
}

internal class DogBreedGalleryViewModelImpl(
    private val dogBreedId: Int,
    private val getDogBreedDataUseCase: GetDogBreedDataUseCase,
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase,
    private val toggleDogBreedAsFavoriteUseCase: ToggleDogBreedAsFavoriteUseCase,
) : DogBreedGalleryViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun dogBreedGalleryViewState(): Flow<PresentableDogBreedGallery> =
        getDogBreedDataUseCase(breedId = dogBreedId).mapLatest {
            val images = getDogBreedImagesUseCase(breedId = dogBreedId)
            // todo impl with real data

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

            PresentableDogBreedGallery(
                breed = PresentableDogBreed(id = dogBreedId, name = "Sausage Dog", isFavorite = false),
                imageUrls = mockedList,
            )
        }

    override fun toggleDogBreedAsFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            toggleDogBreedAsFavoriteUseCase(breedId = dogBreedId)
        }
    }
}
