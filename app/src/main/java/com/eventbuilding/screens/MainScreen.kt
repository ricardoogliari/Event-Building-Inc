package com.eventbuilding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.eventbuilding.R
import com.eventbuilding.data.Category
import com.eventbuilding.navigation.Screens
import com.eventbuilding.state.ItensListState
import com.eventbuilding.state.ItensListViewModel
import com.eventbuilding.ui.theme.backButton
import com.eventbuilding.ui.theme.middleGray
import java.text.NumberFormat

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController, viewModel: ItensListViewModel) {
    val state by viewModel.state.collectAsState()
    val format: NumberFormat = NumberFormat.getCurrencyInstance()

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                    text = "Event Builder",
            color = Color.Black,
            fontSize = TextUnit(value = 18f, type = TextUnitType.Sp),
            fontWeight = FontWeight.W800,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Add to your event to view our cost estimate.",
                color = middleGray,
                fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                fontWeight = FontWeight.W500,
            )
        }
        Text(
            text = format.format(state.selectedItens.fold(0.0) {
                    count, item -> count + item.avgBudget
            }),
            color = Color.Black,
            fontSize = TextUnit(value = 37f, type = TextUnitType.Sp),
            fontWeight = FontWeight.W800,)
        StaggeredGrid(state, navController)
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = backButton,
                contentColor = Color.White,
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                var min = 0.0
                var max = 0.0
                state.selectedItens.forEach {
                    min += it.minBudget
                    max += it.maxBudget
                }
                navController.navigate(
                    route = Screens.Saved.createRoute(format.format(min), format.format(max))
                )
            }) {
            Text(text = "Save")
        }
    }
}

@Composable
fun StaggeredGrid(state: ItensListState, navController: NavController){
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
    ) {
        items(state.categories.size) { index ->
            StaggeredGridMainItem(state.categories[index], state, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaggeredGridMainItem(category: Category, state: ItensListState, navController: NavController) {
    val padding = 16.dp
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            state.itens = emptyList()
            navController.navigate(
                route = Screens.Internal.createRoute(category.title, category.id)
            )
        }
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = category.image,
                contentDescription = null,
            )
            Spacer(Modifier.size(padding))
            Box (modifier = Modifier.padding(padding)) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category.title
                    )
                    Icon(
                        Icons.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        tint = backButton
                    )
                }
            }
        }
    }
}
