package com.example.thebreedexplorerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.thebreedexplorerapp.ui.screens.DogBreedDetailsScreen
import com.example.thebreedexplorerapp.ui.theme.TheBreedExplorerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheBreedExplorerAppTheme {
//                DogBreedsScreen()
                DogBreedDetailsScreen(
                    dogBreedName = "Sausage Dog",
                    onBackClick = {},
                    onAddToFavoritesClick = {},
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheBreedExplorerAppTheme {
        Greeting("Android")
    }
}