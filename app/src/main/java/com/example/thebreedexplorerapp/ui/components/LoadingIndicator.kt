package com.example.thebreedexplorerapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val loadingIndicatorStrokeWidth = 12.dp
private val loadingIndicatorSize = 140.dp

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        strokeWidth = loadingIndicatorStrokeWidth,
        modifier = modifier.size(loadingIndicatorSize),
    )
}