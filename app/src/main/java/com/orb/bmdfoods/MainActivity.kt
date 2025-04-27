package com.orb.bmdfoods

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.orb.bmdfoods.data.FoodItemsData
import com.orb.bmdfoods.data.OptionsReservedExample
import com.orb.bmdfoods.data.ReservedFoodExample
import com.orb.bmdfoods.data.ReservingViewModel
import com.orb.bmdfoods.navigation.MyNavigationHost
import com.orb.bmdfoods.ui.components.ImesTest
import com.orb.bmdfoods.ui.screens.AvailableFoodsScreen
import com.orb.bmdfoods.ui.screens.ReservedScreen
import com.orb.bmdfoods.ui.screens.ReservingProcessScreen
import com.orb.bmdfoods.ui.screens.ReservingScreen
import com.orb.bmdfoods.ui.sections.OptionsSection
import com.orb.bmdfoods.ui.theme.AppTheme
import com.teamapt.monnify.sdk.Monnify
import com.teamapt.monnify.sdk.MonnifyTransactionResponse
import com.teamapt.monnify.sdk.Status
import com.teamapt.monnify.sdk.data.model.TransactionDetails
import com.teamapt.monnify.sdk.service.ApplicationMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val reservingViewModel: ReservingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
        setContent {
            AppTheme {
//                ImesTest()
                MyNavigationHost()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        val response =
            data?.getParcelableExtra<MonnifyTransactionResponse>(ReservingViewModel.KEY_RESULT) as MonnifyTransactionResponse
        response.let {
            reservingViewModel.handlePaymentResponse(it)
            // Show toast if needed
        }

        var message = ""
        message = when(response.status) {
            Status.PENDING -> "Transaction not paid"
            Status.PAID -> "Transaction paid"
            Status.OVERPAID -> "You paid more than expected amount."
            Status.PARTIALLY_PAID -> "You paid less than expected amount."
            Status.FAILED -> "Transaction unsuccessfully"
            Status.PAYMENT_GATEWAY_ERROR -> "Payment gateway error"
            Status.CANCELLED -> "Cancelled"
        }
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }
}