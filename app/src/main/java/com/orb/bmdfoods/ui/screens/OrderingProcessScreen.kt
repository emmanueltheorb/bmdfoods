package com.orb.bmdfoods.ui.screens

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import com.orb.bmdfoods.R
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orb.bmdfoods.data.ReservingViewModel
import com.orb.bmdfoods.ui.components.MyOutlinedTextField
import com.orb.bmdfoods.ui.theme.AppTheme

@Composable
fun OrderingProcessScreen(
    modifier: Modifier = Modifier,
    reservingViewModel: ReservingViewModel = hiltViewModel(viewModelStoreOwner = LocalActivity.current as ComponentActivity),
    navToAvailableScreen: () -> Unit,
    onNavigate: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    val paymentResult by reservingViewModel.paymentResult.collectAsState()
    var address = remember { mutableStateOf("") }
    var phoneNumber = remember { mutableIntStateOf(0) }



    Box(
        modifier
            .systemBarsPadding()
            .padding(horizontal = 18.dp)
    ) {
        Column(
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier.size(35.dp),
                    painter = painterResource(R.drawable.helmet_icon),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null
                )
                Text(
                    text = "ORDER",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = "Foods ordered will be delivered to your address of choice at an added fee of ₦500",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
            Column(
                modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyOutlinedTextField(
                    placeholder = "delivery address",
                    onValueChange = {
                        address.value = it
                    }
                )
                MyOutlinedTextField(
                    placeholder = "Phone Number",
                    onValueChange = {
                        phoneNumber.intValue = it.toInt()
                    },
                    width = 180.dp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
        Button(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            onClick = {
                reservingViewModel.initializePayment(
                    activity = activity,
                    transactionDetails = reservingViewModel.createTransactionDetails()
                )
//                reservingViewModel.orderFood(onComplete = {}, error = {})
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Text(
                text = "Continue",
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrevOrderingProcessScreen() {
    AppTheme {
        OrderingProcessScreen(
            navToAvailableScreen = {}
        ) { }
    }
}