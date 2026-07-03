package com.example.thebreedexplorerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thebreedexplorerapp.R
import com.example.thebreedexplorerapp.ui.components.IconButton
import com.example.thebreedexplorerapp.ui.components.SearchBar
import com.example.thebreedexplorerapp.ui.components.TopBar
import com.example.thebreedexplorerapp.ui.model.PresentableDogBreed
import com.example.thebreedexplorerapp.ui.theme.Purple40
import com.example.thebreedexplorerapp.ui.theme.Purple80
import com.example.thebreedexplorerapp.ui.theme.Red40
import com.example.thebreedexplorerapp.ui.theme.Red80
import com.example.thebreedexplorerapp.ui.theme.Typography
import com.example.thebreedexplorerapp.ui.theme.padding100
import com.example.thebreedexplorerapp.ui.theme.padding200
import com.example.thebreedexplorerapp.ui.theme.padding300

private val cardBorderRadius = 16.dp
private val leadingIconSize = 42.dp

@Composable
fun DogBreedsScreen(
    dogBreedQuery: String,
    dogBreeds: List<PresentableDogBreed>,
    callbacks: DogBreedsScreenCallbacks,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.dog_breeds_title))
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(
                query = dogBreedQuery,
                onQueryChange = callbacks.onQueryChange,
                onClearClick = callbacks.onQueryClear,
                modifier = Modifier.padding(padding200),
            )
            DogBreedList(
                dogBreeds = dogBreeds,
                onItemClick = callbacks.onDogBreedClick,
                onAddDogBreedToFavoriteClick = callbacks.onAddDogBreedToFavoriteClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DogBreedList(
    dogBreeds: List<PresentableDogBreed>,
    onItemClick: (Int) -> Unit,
    onAddDogBreedToFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(padding100),
        modifier = modifier.padding(horizontal = padding200),
    ) {
        items(items = dogBreeds, key = PresentableDogBreed::id) { dogBreed ->
            DogBreedItem(
                dogBreedId = dogBreed.id,
                dogBreedName = dogBreed.name,
                isFavorite = dogBreed.isFavorite,
                onClick = onItemClick,
                onAddToFavoritesClick = onAddDogBreedToFavoriteClick,
            )
        }
    }
}

@Composable
private fun DogBreedItem(
    dogBreedId: Int,
    dogBreedName: String,
    isFavorite: Boolean,
    onClick: (Int) -> Unit,
    onAddToFavoritesClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = cardBorderRadius))
            .background(color = if (isSystemInDarkTheme()) Purple40 else Purple80)
            .clickable(onClick = { onClick(dogBreedId) }),
    ) {
        Text(
            text = dogBreedName,
            style = Typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = padding200,
                    vertical = padding300,
                )
        )

        IconButton(
            iconResId = R.drawable.ic_favorite_filled.takeIf { isFavorite } ?: R.drawable.ic_favorite,
            onClick = { onAddToFavoritesClick(dogBreedId) },
            iconButtonSize = leadingIconSize,
            tint = if (isSystemInDarkTheme()) Red80 else Red40,
            modifier = Modifier.padding(end = padding200)
        )
    }
}

@Preview
@Composable
private fun DogBreedsScreenPreview() {
    DogBreedsScreen(
        dogBreedQuery = "",
        dogBreeds = listOf(
            PresentableDogBreed(id = 1, name = "Sausage Dog", isFavorite = false),
            PresentableDogBreed(id = 2, name = "Golden Retriever", isFavorite = true),
            PresentableDogBreed(id = 3, name = "German Shepherd", isFavorite = false),
        ),
        callbacks = DogBreedsScreenCallbacks(
            onDogBreedClick = {},
            onAddDogBreedToFavoriteClick = {},
            onQueryChange = {},
            onQueryClear = {}
        )
    )
}

data class DogBreedsScreenCallbacks(
    val onDogBreedClick: (Int) -> Unit,
    val onAddDogBreedToFavoriteClick: (Int) -> Unit,
    val onQueryChange: (String) -> Unit,
    val onQueryClear: () -> Unit,
)
