package com.orb.bmdfoods.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orb.bmdfoods.data.FoodItemState
import com.orb.bmdfoods.data.ReservedFoodsScreenState
import com.orb.bmdfoods.data.ReservedFoodsScreenViewModel
import com.orb.bmdfoods.ui.components.ReservedFoodItem

@Composable
fun ReservedFoodsScreen(
    modifier: Modifier = Modifier,
    screenState: ReservedFoodsScreenState = ReservedFoodsScreenState(),
    reservedFoodsViewModel: ReservedFoodsScreenViewModel = hiltViewModel(),
    onFoodItemClicked: () -> Unit
) {
    val headerText = "Reserve & Orders "
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 18.dp)
            .padding(top = 50.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SectionHeader(
            text = headerText
        )
        FoodsListColumn(
            data = screenState.reservedFoodsItemData,
            onFoodItemClicked = onFoodItemClicked
        )
    }
}

@Composable
private fun SectionHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun FoodsListColumn(
    modifier: Modifier = Modifier,
    data: List<FoodItemState>,
    onFoodItemClicked: () -> Unit
) {
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        data.onEach { item ->
            ReservedFoodItem(
                data = item,
                onReserveFoodClicked = onFoodItemClicked
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReservedFoodsScreenPreview() {
    ReservedFoodsScreen {  }
}