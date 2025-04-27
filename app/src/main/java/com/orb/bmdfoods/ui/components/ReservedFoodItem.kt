package com.orb.bmdfoods.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.orb.bmdfoods.data.FoodItemState

@Composable
fun ReservedFoodItem(
    modifier: Modifier = Modifier,
    data: FoodItemState,
    onReserveFoodClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable(onClick = onReserveFoodClicked),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        ReservedFoodImage(foodImage = data.img)
        ReserveDescription(name = data.foodName)
    }
}

@Composable
private fun ReservedFoodImage(
    modifier: Modifier = Modifier,
    @DrawableRes foodImage: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(19.dp)
    ) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(foodImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ReserveDescription(
    modifier: Modifier = Modifier,
    name: String
) {
    val reserveCode = "23568771"
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(80.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = reserveCode,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Green,
            modifier = modifier.wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ReservedFoodsScreenPreview() {
//    AppTheme {
//        ReservedFoodsScreen(onFoodItemClicked = {})
//    }
//}