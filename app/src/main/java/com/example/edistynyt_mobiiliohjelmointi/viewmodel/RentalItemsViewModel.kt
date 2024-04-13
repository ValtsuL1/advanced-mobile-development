package com.example.edistynyt_mobiiliohjelmointi.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyt_mobiiliohjelmointi.api.rentalItemsService
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItemsState
import kotlinx.coroutines.launch

class RentalItemsViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val _categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    private val _rentalItemsState = mutableStateOf(RentalItemsState())
    val rentalItemsState: State<RentalItemsState> = _rentalItemsState

    init {
        getRentalItems()
    }

    private fun getRentalItems() {
        viewModelScope.launch {
            try {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = true)
                val res = rentalItemsService.getItemsByCategory(_categoryId)
                _rentalItemsState.value = rentalItemsState.value.copy(list = res.items)
                Log.d("rental items list", res.toString())
            } catch (e: Exception) {
                _rentalItemsState.value = _rentalItemsState.value.copy(err = e.toString())
            } finally {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = false)
            }
        }
    }
}