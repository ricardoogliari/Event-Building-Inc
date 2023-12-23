package com.eventbuilding.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.eventbuilding.data.Item
import com.eventbuilding.state.ItensListEvent
import com.eventbuilding.state.ItensListState
import com.eventbuilding.state.ItensListViewModel
import com.eventbuilding.ui.theme.EventBuildingTheme
import com.eventbuilding.ui.theme.middleGray
import java.text.NumberFormat

@Composable
fun InternalScreen(modifier: Modifier = Modifier, viewModel: ItensListViewModel, categoryName: String, categoryId: String) {
    val state by viewModel.state.collectAsState()
    viewModel.state.handleEvent(
        ItensListEvent.InternalScreenStarted(categoryId)
    )
    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = categoryName,
            color = Color.Black,
            fontSize = TextUnit(value = 18f, type = TextUnitType.Sp),
            fontWeight = FontWeight.W800,
        )
        Text(
            text = "Add to your event to view our cost estimate.",
            color = middleGray,
            fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
            fontWeight = FontWeight.W500,
        )
        StaggeredGridInternal(state, viewModel)
    }
}

@Composable
fun StaggeredGridInternal(state: ItensListState, viewModel: ItensListViewModel){
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
    ) {
        items(state.itens.size) { index ->
            StaggeredGridItem(state.itens[index], state.selectedItens.contains(state.itens[index])) {
                viewModel.state.handleEvent(
                    ItensListEvent.ActionItem(item = it)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaggeredGridItem(item: Item, contains: Boolean, callback: (item: Item) -> Unit) {
    val padding = 16.dp
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    ElevatedCard (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            callback.invoke(item)
        }
    ) {
        Box (
            contentAlignment = Alignment.TopEnd
        ){
            Column(
                Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = null,
                )
                Spacer(Modifier.size(padding))
                Box(modifier = Modifier.padding(padding)) {
                    Column {
                        Text(
                            text = item.title
                        )
                        Text(
                            text = "${format.format(item.avgBudget)}"
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.padding(padding)
            ) {
                getIcon(contains)
            }
        }
    }
}

@Composable
fun getIcon(contains: Boolean){
    if (contains){
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
        )
    } else {
        Icon(
            Icons.Default.AddCircle,
            contentDescription = null,
        )
    }
}