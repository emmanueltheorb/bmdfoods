package com.orb.bmdfoods.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orb.bmdfoods.data.AvailableFoodsScreenState
import com.orb.bmdfoods.data.AvailableScreenState
import com.orb.bmdfoods.data.AvailableScreenViewModel
import com.orb.bmdfoods.data.FoodItemState
import com.orb.bmdfoods.data.Foods
import com.orb.bmdfoods.data.ScrollViewModel
import com.orb.bmdfoods.repository.Resources
import com.orb.bmdfoods.ui.components.FoodImage
import com.orb.bmdfoods.ui.components.ItemDescription
import com.orb.bmdfoods.ui.components.SearchButton
import com.orb.bmdfoods.ui.theme.AppTheme
import kotlin.toString

@Composable
fun AvailableFoodsScreen(
    modifier: Modifier = Modifier,
    availableFoodsViewModel: AvailableScreenViewModel = hiltViewModel(),
    scrollViewModel: ScrollViewModel = hiltViewModel(),
    onSearchButtonClicked: () -> Unit,
    navToReservingScreen: (Foods, String) -> Unit
) {
    val headerText = "BMD Foods"

    val screenState by availableFoodsViewModel.availableScreenState.collectAsState()

    val lazyListState = rememberLazyListState()
    val scrollThreshold = 150

    val isScrolledBeyondThreshold by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 &&
                    lazyListState.firstVisibleItemScrollOffset <= scrollThreshold
        }
    }

//    LaunchedEffect(Unit) {
//        scrollViewModel.scrollToTop.collect {
//            lazyListState.animateScrollToItem(0)
//        }
//    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        contentAlignment = Alignment.TopEnd
    ) {
        FoodsListColumn(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            data = screenState,
            lazyListState = lazyListState,
            navToReservingScreen = navToReservingScreen
        )
        AnimatedVisibility(
            visible = isScrolledBeyondThreshold,
            exit = slideOutVertically(),
            enter = slideInVertically()
        ) {
            SectionHeader(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 18.dp)
                    .padding(top = 2.5.dp),
                text = headerText
            )
        }
        AnimatedVisibility(
            visible = isScrolledBeyondThreshold,
            exit = slideOutVertically(),
            enter = slideInVertically()
        ) {
            SearchButtonBox(
                onSearchButtonClicked = onSearchButtonClicked
            )
        }
    }
}

@Composable
fun SearchButtonBox(
    modifier: Modifier = Modifier,
    onSearchButtonClicked: () -> Unit
) {
    Box(
        modifier
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        SearchButton(onSearchButtonClicked = onSearchButtonClicked)
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
    lazyListState: LazyListState,
    data: AvailableScreenState,
    navToReservingScreen: (Foods, String) -> Unit
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .height(900.dp),
        contentPadding = PaddingValues(bottom = 10.dp, top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        when (data.foodList) {
            is Resources.Loading -> {
                item(
                    content = {
                        CircularProgressIndicator(
                            modifier = modifier.wrapContentSize(align = Alignment.Center),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
            is Resources.Error -> {
                item(
                    content = {
                        Text(
                            text = data.foodList.throwable?.localizedMessage ?: "Unknown Error",
                            color = Color.Red
                        )
                    }
                )
            }
            is Resources.Success -> {
                items(data.foodList.data ?: emptyList()) { item ->
                    AvailableFoodItemForList(
                        data = item,
                        onFoodItemClicked = {
                            navToReservingScreen(item, item.imgUrl)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AvailableFoodItemForList(
    modifier: Modifier = Modifier,
    data: Foods,
    onFoodItemClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.clickable(onClick = onFoodItemClicked)
    ) {
        FoodImage(
            modifier.height(220.dp),
            foodImage = data.imgUrl
        )
        ItemDescription(name = data.foodName, price = data.price.toString())
    }
}