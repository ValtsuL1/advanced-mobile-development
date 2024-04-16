package com.example.edistynyt_mobiiliohjelmointi.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyt_mobiiliohjelmointi.MainActivity
import com.example.edistynyt_mobiiliohjelmointi.api.rentalItemsService
import com.example.edistynyt_mobiiliohjelmointi.model.EditItemReq
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItem
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItemState
import kotlinx.coroutines.launch

class RentalItemViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val _categoryId = MainActivity.categoryId
    private val _rentalItemId = savedStateHandle.get<String>("rentalItemId")?.toIntOrNull() ?: 0

    private val _rentalItemState = mutableStateOf(RentalItemState())
    val rentalItemState: State<RentalItemState> = _rentalItemState

    init {
        Log.d("ITEM/ CATEGORY ID", _categoryId.toString())
        getRentalItem()
    }

    private fun getRentalItem() {
        viewModelScope.launch {
            try {
                _rentalItemState.value = _rentalItemState.value.copy(loading = true)
                val res = rentalItemsService.getItem(_rentalItemId)
                _rentalItemState.value = _rentalItemState.value.copy(item = res.item)
            } catch (e: Exception) {
                _rentalItemState.value = _rentalItemState.value.copy(err = e.toString())
            } finally {
                _rentalItemState.value = _rentalItemState.value.copy(loading = false)
            }
        }
    }

    fun editRentalItem(goToItems: (Int) -> Unit) {
        viewModelScope.launch {
            try {
                _rentalItemState.value = _rentalItemState.value.copy(loading = true)
                rentalItemsService.editItem(
                    _rentalItemId,
                    EditItemReq(name = _rentalItemState.value.item.name)
                )
                setOk(true)
                goToItems(_categoryId)
            } catch (e: Exception) {
                _rentalItemState.value = _rentalItemState.value.copy(err = e.toString())
            } finally {
                _rentalItemState.value = _rentalItemState.value.copy(loading = false)
            }
        }
    }

    fun setName(newName: String) {
        val item = _rentalItemState.value.item.copy(name = newName)
        _rentalItemState.value = _rentalItemState.value.copy(item = item)
    }

    fun setOk(status: Boolean) {
        _rentalItemState.value = _rentalItemState.value.copy(ok = status)
    }
}