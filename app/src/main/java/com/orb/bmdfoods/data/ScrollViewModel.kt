package com.orb.bmdfoods.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrollViewModel @Inject constructor(): ViewModel() {
    private val _scrollToTop = MutableSharedFlow<Unit>()
    val scrollToTop: SharedFlow<Unit> = _scrollToTop.asSharedFlow()

    fun requestScrollToTop() {
        viewModelScope.launch {
            _scrollToTop.emit(Unit)
        }
    }
}