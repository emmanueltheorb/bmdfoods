package com.orb.bmdfoods.ui.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orb.bmdfoods.R
import com.orb.bmdfoods.data.ReservingViewModel
import com.orb.bmdfoods.data.ReservingViewModel.Companion.KEY_RESULT
import com.teamapt.monnify.sdk.MonnifyTransactionResponse
import com.teamapt.monnify.sdk.Status

@Composable
fun ReservingProcessScreen(
    modifier: Modifier = Modifier,
    reservingViewModel: ReservingViewModel = hiltViewModel(viewModelStoreOwner = LocalActivity.current as ComponentActivity),
    navToAvailableScreen: () -> Unit,
    onNavigate: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    Box(
        modifier
            .systemBarsPadding()
            .padding(horizontal = 18.dp)
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(top = 10.dp)
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
                    painter = painterResource(R.drawable.reserve_icon),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null
                )
                Text(
                    text = "RESERVE",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = "Foods reserved would need to be collected in person from bmd fast foods at no extra cost",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall
            )
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
//                reservingViewModel.reserveFood(onComplete = {}, error = {})
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