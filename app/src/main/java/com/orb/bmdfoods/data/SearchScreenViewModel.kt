package com.orb.bmdfoods.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orb.bmdfoods.repository.Resources
import com.orb.bmdfoods.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: StorageRepository
) : ViewModel() {

    init {
        getFoods()
    }

    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState: StateFlow<SearchScreenState> = _searchScreenState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val foods = searchText
        .combine(searchScreenState) { text, state ->
            if (text.isBlank()) {
                state.foodList
            } else {
                state.foodList.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            searchScreenState.value.foodList
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun cancelSearchText() {
        _searchText.value = ""
    }

    fun showUnAvailableSnackBar() {
        _searchScreenState.update { currentState ->
            currentState.copy(snackBarMessage = SnackBarMessage.Error("Food Unavailable"))
        }
    }

    fun clearSnackBar() {
        _searchScreenState.update { currentState ->
            currentState.copy(snackBarMessage = null)
        }
    }

    private var foodCollectionJob: Job? = null

    fun getFoods() {
        foodCollectionJob?.cancel() // Cancel previous collection

        foodCollectionJob = viewModelScope.launch {
            repository.getFoodList().collect { resources ->
                val foods = resources.data
                _searchScreenState.update { currentState ->
                    currentState.copy(foodList = foods ?: emptyList())
                }
            }
        }
    }
}

data class SearchScreenState(
    val foodList: List<Foods> = emptyList<Foods>(),
    val snackBarMessage: SnackBarMessage? = null
)