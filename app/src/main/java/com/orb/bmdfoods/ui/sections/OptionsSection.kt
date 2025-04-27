package com.orb.bmdfoods.ui.sections

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orb.bmdfoods.R
import com.orb.bmdfoods.data.FoodItemState
import com.orb.bmdfoods.data.Foods
import com.orb.bmdfoods.data.OptionReserved
import com.orb.bmdfoods.data.OptionState
import com.orb.bmdfoods.ui.components.DropdownMenuSelector

@Composable
fun OptionsCheck(
    modifier: Modifier = Modifier,
    data: Foods,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    if (data.options == null) {
        NullOptions(data = data)
    } else {
        OptionsSection(
            options = data.options,
            onPriceChange = onPriceChange,
            onOptionChange = onOptionChange
        )
    }
}

@Composable
fun NullOptions(
    data: Foods?
) {
    var options = data
}

@Composable
fun OptionsSection(
    modifier: Modifier = Modifier,
    options: List<OptionState>,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NotMergedOptions(
            options = options,
            onPriceChange = onPriceChange,
            onOptionChange = onOptionChange
        )
        MergedOptions(
            options = options,
            onPriceChange = onPriceChange,
            onOptionChange = onOptionChange
        )
    }
}

@Composable
fun IncrementSection(
    modifier: Modifier = Modifier,
    amount: Int,
    onDecrementClicked: () -> Unit,
    onIncrementClicked: () -> Unit
) {
    Row(
        modifier = modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyIconButton(
            icon = R.drawable.ic_minus,
            onClicked = onDecrementClicked
        )
        Text(
            text = amount.toString(),
            color = MaterialTheme.colorScheme.onBackground
        )
        MyIconButton(
            icon = R.drawable.ic_plus,
            onClicked = onIncrementClicked
        )
    }
}

@Composable
fun MyIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    onClicked: () -> Unit
) {
    Surface(
        modifier = modifier.size(24.dp),
        shape = CircleShape,
        color = containerColor,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable(onClick = onClicked),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    text: String,
    width: Dp = 110.dp,
    height: Dp = 50.dp
) {
    OutlinedCard(
        modifier = Modifier.padding(16.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(width)
                .height(height)
                .padding(5.dp)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun PriceTextView(
    modifier: Modifier = Modifier,
    price: Int,
    width: Dp = 110.dp
) {
    Surface(
        text = "₦$price",
        width = width
    )
}

@Composable
fun PriceView(
    modifier: Modifier = Modifier,
    price: Int
) {
    Surface(text = "₦$price")
}

fun createNameList(
    options: List<OptionState>,
    mergeGroupNumber: Int
): MutableList<String> {

    val nameList = mutableListOf<String>()

    for (option in options) {
        if (option.mergeGroup == mergeGroupNumber) {
            nameList.add(option.name)
        }
    }

    return nameList
}

fun createNumberList(
    options: List<OptionState>,
    mergeGroupNumber: Int
): MutableList<Int> {

    val numberList = mutableListOf<Int>()

    for (option in options) {
        if (option.mergeGroup == mergeGroupNumber) {
            numberList.add(option.price)
        }
    }

    return numberList
}

@Composable
fun OptionWithoutAmount(
    modifier: Modifier = Modifier,
    option: OptionState,
    key: String,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    var optionState: OptionState = option
    var priceView = optionState.price

    LaunchedEffect(priceView) {
        onPriceChange(key, priceView)
        onOptionChange(
            key,
            OptionReserved(
                id = option.id,
                name = option.name,
                amount = option.amount,
                price = priceView
            )
        )
    }

    DisposableEffect(key) {
        onDispose {
            onPriceChange(key, 0)
            onOptionChange(
                key,
                OptionReserved(
                    0,
                    "",
                    null,
                    0
                )
            )
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(text = optionState.name)
        PriceTextView(
            price = priceView
        )
    }
}

@Composable
fun OptionWithAmount(
    modifier: Modifier = Modifier,
    option: OptionState,
    key: String,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    var optionState: OptionState = option
    var amountView by remember { mutableIntStateOf(optionState.amount!!) }
    val lowerLimit = optionState.lowerLimit!!
    val upperLimit = optionState.upperLimit!!
    var priceView = optionState.price
    var price0 = priceView * amountView

    LaunchedEffect(price0) {
        onPriceChange(key, price0)
        onOptionChange(
            key,
            OptionReserved(
                id = option.id,
                name = option.name,
                amount = amountView,
                price = price0
            )
        )
    }

    DisposableEffect(key) {
        onDispose {
            onPriceChange(key, 0)
            onOptionChange(
                key,
                OptionReserved(
                    0,
                    "",
                    0,
                    0
                )
            )
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(text = optionState.name)
        IncrementSection(
            amount = amountView,
            onDecrementClicked = {
                if (amountView > lowerLimit) {
                    amountView--
                }
            },
            onIncrementClicked = {
                if (amountView < upperLimit) {
                    amountView++
                }
            }
        )
        PriceTextView(
            price = price0
        )
    }
}

@Composable
fun MergedOptions(
    modifier: Modifier = Modifier,
    options: List<OptionState>,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    val mergedGroups = remember(options) {
        options.groupBy { it.mergeGroup }
            .filterKeys { it != null }
            .toList()
            .sortedBy { (groupNumber, _) -> groupNumber }
    }

    mergedGroups.forEach { (groupNumber, groupOptions) ->
        val groupKey = "merged_${groupNumber}"
        val hasAmount = groupOptions.any { it.amount != null }

        // State hoisted to parent component
        var selectedIndex by remember(groupKey) { mutableIntStateOf(0) }
        var currentAmount by remember(groupKey) {
            mutableIntStateOf(
                groupOptions.firstOrNull()?.amount ?: 1
            )
        }

        if (hasAmount) {
            MergedOptionsWithAmount(
                modifier = modifier,
                options = groupOptions,
                mergeGroupNumber = groupNumber!!,
                selectedIndex = selectedIndex,
                currentAmount = currentAmount,
                onSelectionChange = { newIndex ->
                    selectedIndex = newIndex
                    // Reset amount when option changes
                    currentAmount = groupOptions[newIndex].amount ?: 1
                },
                onAmountChange = { newAmount ->
                    currentAmount = newAmount
                },
                key = groupKey,
                onPriceChange = onPriceChange,
                onOptionChange = onOptionChange
            )
        } else {
            MergedOptionsWithoutAmount(
                modifier = modifier,
                options = groupOptions,
                mergeGroupNumber = groupNumber!!,
                selectedIndex = selectedIndex,
                onSelectionChange = { newIndex ->
                    selectedIndex = newIndex
                },
                key = groupKey,
                onPriceChange = onPriceChange,
                onOptionChange = onOptionChange
            )
        }
    }
}

@Composable
fun NotMergedOptions(
    modifier: Modifier = Modifier,
    options: List<OptionState>,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    for (option in options) {
        val key = "option_${option.id}"
        if (option.mergeGroup == null && option.amount == null) {
            OptionWithoutAmount(
                option = option,
                key = key,
                onPriceChange = onPriceChange,
                onOptionChange = onOptionChange
            )
        } else {
            if (option.mergeGroup == null) {
                OptionWithAmount(
                    option = option,
                    key = key,
                    onPriceChange = onPriceChange,
                    onOptionChange = onOptionChange
                )
            }
        }
    }
}

@Composable
fun MergedOptionsWithAmount(
    modifier: Modifier = Modifier,
    options: List<OptionState>,
    mergeGroupNumber: Int,
    selectedIndex: Int,          // Controlled from parent
    currentAmount: Int,          // Controlled from parent
    onSelectionChange: (Int) -> Unit,  // Dropdown callback
    onAmountChange: (Int) -> Unit,     // Increment/Decrement callback
    onOptionChange: (String, OptionReserved) -> Unit,
    key: String,
    onPriceChange: (String, Int) -> Unit
) {
    val option = options[selectedIndex]
    val numberList =
        createNumberList(options, mergeGroupNumber)
    val currentPrice = numberList[selectedIndex] * currentAmount

    LaunchedEffect(currentPrice) {
        onPriceChange(key, currentPrice)
        onOptionChange(
            key,
            OptionReserved(
                id = option.id,
                name = option.name,
                amount = currentAmount,
                price = currentPrice
            )
        )
    }

    DisposableEffect(key) {
        onDispose {
            onPriceChange(key, 0)
            onOptionChange(
                key,
                OptionReserved(
                    0,
                    "",
                    0,
                    0
                )
            )
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropdownMenuSelector(
            options = options,
            mergeGroupNumber = mergeGroupNumber,
            selectedIndex = selectedIndex,
            onSelectionChange = onSelectionChange
        )

        IncrementSection(
            amount = currentAmount,
            onDecrementClicked = {
                if (currentAmount > option.lowerLimit!!) {
                    onAmountChange(currentAmount - 1)
                }
            },
            onIncrementClicked = {
                if (currentAmount < option.upperLimit!!) {
                    onAmountChange(currentAmount + 1)
                }
            }
        )

        PriceView(price = currentPrice)
    }
}

@Composable
fun MergedOptionsWithoutAmount(
    modifier: Modifier = Modifier,
    options: List<OptionState>,
    mergeGroupNumber: Int,
    selectedIndex: Int,          // Controlled from parent
    onSelectionChange: (Int) -> Unit,  // Dropdown callback
    key: String,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    val numberList = createNumberList(options, mergeGroupNumber)
    val currentPrice = numberList[selectedIndex]

    LaunchedEffect(currentPrice) {
        onPriceChange(key, currentPrice)
        onOptionChange(
            key,
            OptionReserved(
                id = options[selectedIndex].id,
                name = options[selectedIndex].name,
                amount = options[selectedIndex].amount,
                price = currentPrice
            )
        )
    }

    DisposableEffect(key) {
        onDispose {
            onPriceChange(key, 0)
            onOptionChange(
                key,
                OptionReserved(
                    0,
                    "",
                    null,
                    0
                )
            )
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropdownMenuSelector(
            options = options,
            mergeGroupNumber = mergeGroupNumber,
            selectedIndex = selectedIndex,
            onSelectionChange = onSelectionChange
        )
        PriceView(price = currentPrice)
    }
}