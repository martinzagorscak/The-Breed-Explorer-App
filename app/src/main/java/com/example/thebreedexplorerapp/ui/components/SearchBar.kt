package com.example.thebreedexplorerapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thebreedexplorerapp.R
import com.example.thebreedexplorerapp.ui.theme.padding100
import com.example.thebreedexplorerapp.ui.theme.padding200

private val trailingIconSize = 36.dp

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(text = "Search...") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Button(
                    iconResId = R.drawable.ic_clear,
                    onClick = onClearClick,
                    iconButtonSize = trailingIconSize,
                    modifier = Modifier.padding(end = padding100)
                )
            }
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(padding100),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SearchBarPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(padding200)) {
        SearchBar(
            query = "",
            onQueryChange = {},
            onClearClick = {}
        )
        SearchBar(
            query = "Golden Retriever",
            onQueryChange = {},
            onClearClick = {}
        )
    }
}
