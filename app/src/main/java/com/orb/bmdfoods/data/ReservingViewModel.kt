package com.orb.bmdfoods.data

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import com.orb.bmdfoods.MainActivity
import com.orb.bmdfoods.repository.StorageRepository
import com.teamapt.monnify.sdk.Monnify
import com.teamapt.monnify.sdk.MonnifyTransactionResponse
import com.teamapt.monnify.sdk.Status
import com.teamapt.monnify.sdk.data.model.TransactionDetails
import com.teamapt.monnify.sdk.service.ApplicationMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ReservingViewModel @Inject constructor(
    private val repository: StorageRepository
): ViewModel() {
    private val monnify = Monnify.instance
    private val _paymentResult = MutableStateFlow<MonnifyTransactionResponse?>(null)
    val paymentResult: StateFlow<MonnifyTransactionResponse?> = _paymentResult

    private var foodItem: ReservedFood = ReservedFood()

    init {
        configureMonnify()
    }

    fun takeReservedFoodItem(food: ReservedFood) {
        foodItem = food
    }

    fun takeOrderedFoodItem(food: ReservedFood) {
        foodItem = food
    }

    fun purchaseFood() {
        if (foodItem.status == "reserved") {
            repository.reserveFood(foodItem, onComplete = {}, error = {})
        }
        if (foodItem.status == "ordered") {
            repository.orderFood(foodItem, onComplete = {}, error = {})
        }
    }

    fun reserveFood(
        onComplete: (Boolean) -> Unit,
        error: (String) -> Unit
    ) {
        repository.reserveFood(foodItem, onComplete, error)
    }

    fun orderFood(
        onComplete: (Boolean) -> Unit,
        error: (String) -> Unit
    ) {
        repository.orderFood(foodItem, onComplete, error)
    }

    private fun configureMonnify() {
        monnify.setApiKey("MK_TEST_FY43QP8MK8")
        monnify.setContractCode("6177634856")
        monnify.setApplicationMode(ApplicationMode.TEST)
    }

    fun createTransactionDetails(): TransactionDetails {
        val randomNumber = Random.nextInt(1, 1_000_000_001)

        return TransactionDetails.Builder()
            .amount(BigDecimal(foodItem.price))
            .currencyCode("NGN")
            .customerName("Orb")
            .customerEmail("obk@gmail.com")
            .paymentReference("Orb_$randomNumber")
            .paymentDescription("Testing")
            .build()
    }

    fun initializePayment(activity: Activity, transactionDetails: TransactionDetails) {
        monnify.initializePayment(
            activity,
            transactionDetails,
            INITIATE_PAYMENT_REQUEST_CODE,
            KEY_RESULT
        )
    }

    fun handlePaymentResponse(response: MonnifyTransactionResponse) {
        _paymentResult.value = response

        if (response.status == Status.PAID) {
            when (foodItem.status) {
                "reserved" -> repository.reserveFood(foodItem,
                    onComplete = { Log.d("ReservingVM", "Reserve success: $it") },
                    error = { Log.e("ReservingVM", "Reserve error: $it") })
                "ordered" -> repository.orderFood(foodItem,
                    onComplete = { Log.d("ReservingVM", "Order success: $it") },
                    error = { Log.e("ReservingVM", "Order error: $it") })
            }
        }
    }

    companion object {
        const val INITIATE_PAYMENT_REQUEST_CODE = 1234567890
        const val KEY_RESULT = "PaymentResult"
    }
}