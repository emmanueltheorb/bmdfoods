package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.orb.bmdfoods.R
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orb.bmdfoods.data.FoodItemState
import com.orb.bmdfoods.data.FoodItemsData
import com.orb.bmdfoods.ui.sections.IncrementSection
import com.orb.bmdfoods.ui.theme.AppTheme

@Composable
fun QuantityBottomBar(
    modifier: Modifier = Modifier,
    price: Int,
    itemPrice: Int,
    upperLimit: Int,
    onQuantityChange: (Int) -> Unit,
    onPriceChange: (Int) -> Unit,
    onOrderClicked: () -> Unit,
    onReserveClicked: () -> Unit
) {
    var quantity by remember { mutableIntStateOf(1) }
    var priceView by remember { mutableIntStateOf(itemPrice) }
    if (price != 0) {
        priceView = price
    }
    var newPrice = priceView * quantity

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalDivider()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .navigationBarsPadding()
                .then(modifier.padding(horizontal = 18.dp))
                .heightIn(min = 56.dp)
        ) {
            Text(
                text = "Quantity",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = modifier.width(10.dp))
            IncrementSection(
                amount = quantity,
                onDecrementClicked = {
                    if (quantity > 1) {
                        quantity--
                        onQuantityChange(quantity)
                        onPriceChange(newPrice)
                    }
                },
                onIncrementClicked = {
                    if (quantity < upperLimit) {
                        quantity++
                        onQuantityChange(quantity)
                        onPriceChange(newPrice)
                    }
                }
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                text = "₦$newPrice",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = modifier.width(15.dp))
            Button(
                modifier = modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                ),
                contentPadding = PaddingValues(horizontal = 4.dp),
                shape = RoundedCornerShape(35.dp),
                onClick = {
                    onOrderClicked()
                }
            ) {
                Icon(
                    modifier = modifier.padding(6.dp),
                    painter = painterResource(R.drawable.helmet_icon),
                    contentDescription = "Order Button"
                )
            }
            Spacer(modifier = modifier.width(5.dp))
            Button(
                modifier = modifier
                    .weight(1f)
                    .height(40.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                ),
                contentPadding = PaddingValues(horizontal = 4.dp),
                shape = RoundedCornerShape(35.dp),
                onClick = {
                    onReserveClicked()
                }
            ) {
                Icon(
                    modifier = modifier.padding(8.dp),
                    painter = painterResource(R.drawable.reserve_icon),
                    contentDescription = "Reserve Button"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrevQuantityBottomBar() {
    AppTheme {
        QuantityBottomBar(
            price = 2500,
            itemPrice = 2600,
            upperLimit = 7,
            onReserveClicked = {},
            onOrderClicked = {},
            onQuantityChange = {},
            onPriceChange = {}
        )
    }
}