package com.orb.bmdfoods.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orb.bmdfoods.repository.Resources
import com.orb.bmdfoods.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableScreenViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val repository: StorageRepository
): ViewModel() {
    val isOnline = networkMonitor.isOnline

    private val _availableScreenState = MutableStateFlow(AvailableScreenState())
    val availableScreenState: StateFlow<AvailableScreenState> = _availableScreenState.asStateFlow()

    private var foodCollectionJob: Job? = null

    val user = repository.user()
    val hasUser: Boolean
        get() = repository.hasUser()
    private val userId: String
        get() = repository.getUserId()

    init {
        getAvailableFoods()
    }

    fun getAvailableFoods() {
        foodCollectionJob?.cancel() // Cancel previous collection

        if (hasUser) {
            foodCollectionJob = viewModelScope.launch {
                repository.getFoodList().collect { resources ->
                    val availableFoods = resources.data?.filter {
                        it.availability == true
                    }
                    _availableScreenState.update { currentState ->
                        currentState.copy(foodList = Resources.Success(availableFoods))
                    }
                }
            }
        } else {
            _availableScreenState.update { currentState ->
                currentState.copy(
                    foodList = Resources.Error(
                        throwable = Throwable("User is not logged In")
                    )
                )
            }
        }
    }
}

data class AvailableScreenState(
    val foodList: Resources<List<Foods>> = Resources.Loading(),
    val snackBarMessage: SnackBarMessage? = null
)