package com.orb.bmdfoods.ui.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.orb.bmdfoods.data.FoodExample
import com.orb.bmdfoods.data.Foods
import com.orb.bmdfoods.data.OptionReserved
import com.orb.bmdfoods.data.ReservedFood
import com.orb.bmdfoods.ui.sections.OptionsCheck
import com.orb.bmdfoods.data.ReservingScreenState
import com.orb.bmdfoods.data.ReservingViewModel
import com.orb.bmdfoods.ui.components.AvailableFoodItem
import com.orb.bmdfoods.ui.components.QuantityBottomBar
import com.orb.bmdfoods.ui.sections.AvailabilitySection
import com.orb.bmdfoods.ui.theme.AppTheme

@Composable
fun ReservingScreen(
    modifier: Modifier = Modifier,
    reservingViewModel: ReservingViewModel = hiltViewModel(viewModelStoreOwner = LocalActivity.current as ComponentActivity),
    food: Foods,
    foodImg: String,
    navToReserveProcess: () -> Unit,
    navToOrderProcess: () -> Unit,
    onNavigate: () -> Unit
) {
    val foodAmount: Int? by remember { mutableStateOf(food.amount) }
    var amount by remember { mutableIntStateOf(1) }
    amount = if (foodAmount == null) {
        5
    } else {
        foodAmount!!
    }
    val priceMap = remember { mutableStateMapOf<String, Int>() }
    val optionMap = remember { mutableStateMapOf<String, OptionReserved>() }
    val totalPrice by remember { derivedStateOf { priceMap.values.sum() } }
    var quantityCallback by remember { mutableIntStateOf(0) }
    var priceCallback by remember { mutableIntStateOf(0) }

    LaunchedEffect(food.id) {
        priceMap.clear()
        optionMap.clear()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        ScreenContent(
            modifier = modifier
                .fillMaxSize(),
            food = food.copy(imgUrl = foodImg),
            onPriceChange = { key, price ->
                priceMap[key] = price
            },
            onOptionChange = { key, option ->
                optionMap[key] = option
            }
        )
        QuantityBottomBar(
            price = totalPrice,
            itemPrice = food.price,
            upperLimit = amount,
            onOrderClicked = {
                reservingViewModel.takeOrderedFoodItem(
                    food = ReservedFood(
                        imgUrl = foodImg,
                        name = food.foodName,
                        price = priceCallback + 500,
                        quantity = quantityCallback,
                        options = optionMap.values.toList(),
                        status = "ordered"
                    )
                )
                navToOrderProcess()
            },
            onReserveClicked = {
                reservingViewModel.takeReservedFoodItem(
                    food = ReservedFood(
                        imgUrl = foodImg,
                        name = food.foodName,
                        price = priceCallback,
                        quantity = quantityCallback,
                        options = optionMap.values.toList(),
                        status = "reserved"
                    )
                )
                navToReserveProcess()
            },
            onPriceChange = {
                priceCallback = it
            },
            onQuantityChange = {
                quantityCallback = it
            }
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    food: Foods,
    onPriceChange: (String, Int) -> Unit,
    onOptionChange: (String, OptionReserved) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        AvailableFoodItem(
            modifier = modifier.padding(horizontal = 18.dp),
            data = food,
            onFoodItemClicked = {}
        )
        AvailabilitySection(
            data = food
        )
        OptionsCheck(
            data = food,
            onPriceChange = onPriceChange,
            onOptionChange = onOptionChange
        )
        Spacer(
            modifier = modifier
                .padding(bottom = 56.dp)
                .height(8.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
fun ReservingScreenPreview() {
    AppTheme {
        ReservingScreen(
            food = FoodExample,
            foodImg = "",
            navToReserveProcess = {},
            navToOrderProcess = {},
            onNavigate = {}
        )
    }
}