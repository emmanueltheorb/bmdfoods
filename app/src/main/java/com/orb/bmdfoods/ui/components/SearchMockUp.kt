package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.Image
import com.orb.bmdfoods.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.GlideImage
import com.orb.bmdfoods.data.FoodItemState
import com.orb.bmdfoods.data.FoodItemsData
import com.orb.bmdfoods.data.Foods
import com.orb.bmdfoods.ui.theme.AppTheme
import com.orb.bmdfoods.ui.theme.RobotoFontFamily
import com.orb.bmdfoods.ui.theme.SurfaceVariant
import com.orb.bmdfoods.ui.theme.White

@Composable
fun SearchMockUp(
    modifier: Modifier = Modifier,
    foods: List<FoodItemState> = FoodItemsData
) {
    val textValue = remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .systemBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = modifier
                .height(40.dp)
                .fillMaxWidth(),
            color = SurfaceVariant,
            shape = RoundedCornerShape(16.dp)
        ) {
            BasicTextField(
                value = textValue.value,
                onValueChange = {
                    textValue.value = it
                },
                modifier = modifier
                    .fillMaxSize()
                    .align(Alignment.CenterStart)
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
                    Box {
                        if (textValue.value.isEmpty()) {
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
        }
        LazyColumn(
            modifier = modifier
                .padding(top = 50.dp)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(foods) { item ->
                SearchScreenFoodItem2(
                    food = item,
                    onFoodClicked = {

                    }
                )
            }
        }
    }
}

@Composable
fun SearchScreenFoodItem2(
    modifier: Modifier = Modifier,
    food: FoodItemState,
    onFoodClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .clickable(onClick = onFoodClicked),
        shape = RoundedCornerShape(9.dp)
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            FoodImg(food = food)
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .alpha(.2f), color = Color.Black
            ) {}
            Text(
                text = food.foodName,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

@Composable
private fun FoodImg(
    modifier: Modifier = Modifier,
    food: FoodItemState
) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(food.img),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = if (food.availability == true) {
            null
        } else {
            ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        }
    )
}

@Preview
@Composable
private fun PrevSearchMockUp() {
    AppTheme {
        SearchMockUp()
    }
}