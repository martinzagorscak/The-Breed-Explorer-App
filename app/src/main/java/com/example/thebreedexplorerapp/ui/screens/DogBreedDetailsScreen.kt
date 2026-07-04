package com.example.thebreedexplorerapp.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.thebreedexplorerapp.R
import com.example.thebreedexplorerapp.ui.components.CTA
import com.example.thebreedexplorerapp.ui.components.FallbackState
import com.example.thebreedexplorerapp.ui.components.IconButton
import com.example.thebreedexplorerapp.ui.components.LoadingIndicator
import com.example.thebreedexplorerapp.ui.components.TopBar
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreed
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreedGallery
import com.example.thebreedexplorerapp.ui.theme.Red40
import com.example.thebreedexplorerapp.ui.theme.Red80
import com.example.thebreedexplorerapp.ui.theme.Typography
import com.example.thebreedexplorerapp.ui.viewmodel.DogBreedGalleryViewState
import com.example.thebreedexplorerapp.ui.viewmodel.EMPTY

private val galleryGridCellsMinWidth = 200.dp
private val topBarIconSize = 36.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedDetailsScreen(
    dogBreedGalleryViewState: DogBreedGalleryViewState,
    callbacks: DogBreedsDetailsScreenCallbacks,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            val title = when (dogBreedGalleryViewState) {
                is DogBreedGalleryViewState.Loaded -> stringResource(
                    R.string.dog_breed_gallery_title_template,
                    dogBreedGalleryViewState.gallery.breed.name
                )

                DogBreedGalleryViewState.Error, DogBreedGalleryViewState.Loading -> EMPTY
            }

            TopBar(
                title = title,
                leadingContent = {
                    IconButton(
                        iconResId = R.drawable.ic_back,
                        onClick = callbacks.onBackClick,
                        iconButtonSize = topBarIconSize,
                    )
                },
                trailingContent = {
                    if (dogBreedGalleryViewState is DogBreedGalleryViewState.Loaded) {
                        val iconResId =
                            R.drawable.ic_favorite_filled.takeIf { dogBreedGalleryViewState.gallery.breed.isFavorite } ?: R.drawable.ic_favorite

                        IconButton(
                            iconResId = iconResId,
                            onClick = { callbacks.onToggleDogBreedAsFavorite() },
                            iconButtonSize = topBarIconSize,
                            tint = if (isSystemInDarkTheme()) Red80 else Red40,
                        )
                    }
                },
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when (dogBreedGalleryViewState) {
                is DogBreedGalleryViewState.Loaded -> {
                    if (dogBreedGalleryViewState.gallery.imageUrls.isNotEmpty()) {
                        Gallery(
                            imageUrls = dogBreedGalleryViewState.gallery.imageUrls,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        EmptyGallery(dogBreedName = dogBreedGalleryViewState.gallery.breed.name)
                    }
                }

                DogBreedGalleryViewState.Error -> {
                    FallbackState(
                        title = stringResource(R.string.dog_breed_not_found_message),
                        cta = CTA(
                            label = stringResource(R.string.navigate_back),
                            onClick = callbacks.onBackClick,
                        )
                    )
                }

                DogBreedGalleryViewState.Loading -> {
                    LoadingIndicator()
                }
            }
        }
    }
}

@Composable
private fun Gallery(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = galleryGridCellsMinWidth),
        modifier = modifier,
    ) {
        items(imageUrls) { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(R.drawable.dog_placeholder),
                fallback = painterResource(R.drawable.dog_fallback),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
private fun EmptyGallery(
    dogBreedName: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.dog_breed_empty_gallery_message, dogBreedName),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview
@Composable
private fun DogBreedDetailsScreenPreview() {
    DogBreedDetailsScreen(
        dogBreedGalleryViewState = DogBreedGalleryViewState.Loaded(
            gallery = PresentableDogBreedGallery(
                breed = PresentableDogBreed(id = 1, name = "Sausage Dog", isFavorite = false),
                imageUrls = emptyList(),
            )
        ),
        callbacks = DogBreedsDetailsScreenCallbacks(
            onBackClick = {},
            onToggleDogBreedAsFavorite = {},
        )
    )
}

data class DogBreedsDetailsScreenCallbacks(
    val onBackClick: () -> Unit,
    val onToggleDogBreedAsFavorite: () -> Unit,
)
