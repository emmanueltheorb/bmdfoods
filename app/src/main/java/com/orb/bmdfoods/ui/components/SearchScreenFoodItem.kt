package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.orb.bmdfoods.data.Foods
import kotlin.code

@Composable
fun SearchScreenFoodItem(
    modifier: Modifier = Modifier,
    food: Foods,
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun FoodImg(
    modifier: Modifier = Modifier,
    food: Foods
) {
    GlideImage(
        modifier = modifier.fillMaxSize(),
        model = food.imgUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = if (food.availability == true) {
            null
        } else {
            ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        }
    )
}