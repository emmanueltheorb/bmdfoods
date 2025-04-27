package com.orb.bmdfoods.data

import androidx.lifecycle.ViewModel
import com.orb.bmdfoods.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReservedFoodsScreenViewModel @Inject constructor(
    private val repository: StorageRepository
): ViewModel() {

}