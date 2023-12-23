package com.eventbuilding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eventbuilding.state.ItensListViewModel
import com.eventbuilding.ui.theme.EventBuildingTheme
import androidx.navigation.compose.rememberNavController
import com.eventbuilding.navigation.NavGraph
import com.eventbuilding.state.ItensListEvent

class MainActivity : ComponentActivity() {

    private val viewModel: ItensListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.handleEvent(
            ItensListEvent.CategoriesScreenStarted
        )
        setContent {
            EventBuildingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ContentButton(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EventBuildingTheme {
        ContentButton("Android")
    }
}