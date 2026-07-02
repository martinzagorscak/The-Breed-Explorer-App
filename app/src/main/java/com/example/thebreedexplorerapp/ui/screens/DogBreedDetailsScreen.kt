package com.example.thebreedexplorerapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.thebreedexplorerapp.R
import com.example.thebreedexplorerapp.ui.components.Button
import com.example.thebreedexplorerapp.ui.components.TopBar

private val galleryGridCellsMinWidth = 200.dp
private val topBarIconSize = 36.dp

@Composable
fun DogBreedDetailsScreen(
    dogBreedName: String,
    onBackClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.dog_breed_gallery_title_template, dogBreedName),
                leadingContent = {
                    Button(
                        iconResId = R.drawable.ic_back,
                        onClick = onBackClick,
                        iconButtonSize = topBarIconSize,
                    )
                },
                trailingContent = {
                    Button(
                        iconResId = R.drawable.ic_favorite,
                        onClick = onAddToFavoritesClick,
                        iconButtonSize = topBarIconSize,
                    )
                },
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
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
        Gallery(
            imageUrls = mockedList, // TODO replace with real data
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        )
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

@Preview
@Composable
private fun DogBreedDetailsScreenPreview() {
    DogBreedDetailsScreen(
        dogBreedName = "Sausage Dog",
        onBackClick = {},
        onAddToFavoritesClick = {},
    )
}
