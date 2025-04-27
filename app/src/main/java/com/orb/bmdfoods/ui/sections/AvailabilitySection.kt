package com.orb.bmdfoods.ui.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orb.bmdfoods.data.FoodItemState
import com.orb.bmdfoods.data.Foods

@Composable
fun AvailabilitySection(
    modifier: Modifier = Modifier,
    data: Foods
) {
    var amountData: Int? by remember { mutableStateOf(null) }
    amountData = data.amount
    Row(
        modifier = modifier.padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Available",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
        AmountAvailable(data = amountData)
    }
}

@Composable
fun AmountAvailable(
    modifier: Modifier = Modifier,
    data: Int?
) {
    if (data == null) {
        NullAmount(data = data)
    } else {
        AmountSurface(data = data)
    }

}

@Composable
fun NullAmount(
    modifier: Modifier = Modifier,
    data: FoodItemState?
) {
    data
}

@Composable
fun AmountSurface(
    modifier: Modifier = Modifier,
    data: Int
) {
    Surface(
        modifier = modifier.size(24.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}