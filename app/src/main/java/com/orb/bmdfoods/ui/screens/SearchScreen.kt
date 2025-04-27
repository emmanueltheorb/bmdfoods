package com.orb.bmdfoods.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import com.orb.bmdfoods.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orb.bmdfoods.data.Foods
import com.orb.bmdfoods.data.SearchScreenViewModel
import com.orb.bmdfoods.ui.components.CustomSnackBarVisuals
import com.orb.bmdfoods.ui.components.MySnackBar
import com.orb.bmdfoods.ui.components.SearchScreenFoodItem
import com.orb.bmdfoods.ui.theme.RobotoFontFamily
import com.orb.bmdfoods.ui.theme.SurfaceVariant
import com.orb.bmdfoods.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
    onFoodClicked: (Foods, String) -> Unit
) {
    val screenState by searchScreenViewModel.searchScreenState.collectAsState()

    val searchText by searchScreenViewModel.searchText.collectAsState()
    val foods by searchScreenViewModel.foods.collectAsState()
    var availability by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val snackBarMessage = screenState.snackBarMessage
    val snackBarHostState = remember { SnackbarHostState() }
    val buttonSignal by remember {
        derivedStateOf {
            searchText.isNotEmpty()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = modifier
                    .navigationBarsPadding(),
                snackbar = { data ->
                    MySnackBar(snackBarData = data)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(8.dp)
                .systemBarsPadding(),
            contentAlignment = Alignment.TopCenter
        ) {
            SearchBar(
                textValue = searchText,
                onValueChange = searchScreenViewModel::onSearchTextChange,
                onCancelClicked = {
                    searchScreenViewModel.cancelSearchText()
                },
                cancelButtonSignal = buttonSignal
            )
            LazyColumn(
                modifier = modifier
                    .padding(top = 50.dp)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(foods) { item ->
                    SearchScreenFoodItem(
                        food = item,
                        onFoodClicked = {
                            availability = item.availability
                            if (availability) {
                                onFoodClicked(item, item.imgUrl)
                            } else {
                                searchScreenViewModel.showUnAvailableSnackBar()
                            }
                        }
                    )
                }
            }
            LaunchedEffect(snackBarMessage) {
                snackBarMessage?.let { message ->
                    scope.launch {
                        val visuals = CustomSnackBarVisuals(
                            message = message.message,
                            actionLabel = null,
                            duration = if (message.isError) SnackbarDuration.Short else SnackbarDuration.Short,
                            withDismissAction = true,
                            isError = message.isError
                        )

                        val result = snackBarHostState.showSnackbar(visuals)

                        // Clear message after dismissal
                        when (result) {
                            SnackbarResult.Dismissed -> searchScreenViewModel.clearSnackBar()
                            SnackbarResult.ActionPerformed -> searchScreenViewModel.clearSnackBar()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    textValue: String,
    onValueChange: (String) -> Unit,
    onCancelClicked: () -> Unit,
    cancelButtonSignal: Boolean
) {
    Surface(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth(),
        color = SurfaceVariant,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row {
            BasicTextField(
                value = textValue,
                onValueChange = onValueChange,
                modifier = modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(vertical = 4.dp)
                    .padding(horizontal = 11.dp),
                maxLines = 1,
                textStyle = TextStyle(
                    color = White,
                    fontFamily = RobotoFontFamily,
                    fontSize = 20.sp
                ),
                cursorBrush = SolidColor(
                    White
                ),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (textValue.isEmpty()) {
                            Text(
                                modifier = modifier.align(Alignment.CenterStart),
                                text = "Search foods",
                                style = MaterialTheme.typography.displayLarge,
                                color = White
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Spacer(modifier.width(10.dp))
            if (cancelButtonSignal) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(horizontal = 11.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .size(20.dp)
                            .clickable(onClick = onCancelClicked),
                        painter = painterResource(R.drawable.cancel),
                        contentDescription = "delete search text",
                        tint = White
                    )
                }
            }
        }
    }
}