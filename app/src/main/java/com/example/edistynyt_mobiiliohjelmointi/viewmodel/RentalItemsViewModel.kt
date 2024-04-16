package com.example.edistynyt_mobiiliohjelmointi.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyt_mobiiliohjelmointi.AccountDatabase
import com.example.edistynyt_mobiiliohjelmointi.DbProvider
import com.example.edistynyt_mobiiliohjelmointi.MainActivity
import com.example.edistynyt_mobiiliohjelmointi.api.authService
import com.example.edistynyt_mobiiliohjelmointi.api.rentalItemsService
import com.example.edistynyt_mobiiliohjelmointi.model.AddItemReq
import com.example.edistynyt_mobiiliohjelmointi.model.AddRentalItemState
import com.example.edistynyt_mobiiliohjelmointi.model.DeleteRentalItemState
import com.example.edistynyt_mobiiliohjelmointi.model.RentItemReq
import com.example.edistynyt_mobiiliohjelmointi.model.RentItemState
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItemState
import com.example.edistynyt_mobiiliohjelmointi.model.RentalItemsState
import kotlinx.coroutines.launch
import kotlin.math.log

class RentalItemsViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    private val _categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    private val _rentalItemsState = mutableStateOf(RentalItemsState())
    val rentalItemsState: State<RentalItemsState> = _rentalItemsState

    private val _rentalItemState = mutableStateOf(RentalItemState())
    val rentalItemState: State<RentalItemState> = _rentalItemState

    private val _deleteRentalItemState = mutableStateOf(DeleteRentalItemState())
    val deleteRentalItemState: State<DeleteRentalItemState> = _deleteRentalItemState

    private val _addRentalItemState = mutableStateOf(AddRentalItemState())
    val addRentalItemState: State<AddRentalItemState> = _addRentalItemState

    private val _rentItemState = mutableStateOf(RentItemState())
    val rentItemState: State<RentItemState> = _rentItemState

    init {
        MainActivity.categoryId = _categoryId
        Log.d("RENTER ID", MainActivity.userId.toString())
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
                Log.d("CATEGORY ID", _categoryId.toString())
                _rentalItemsState.value = _rentalItemsState.value.copy(err = e.toString())
            } finally {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = false)
            }
        }
    }

    fun verifyItemRental(rentalItemId: Int) {
        _re
    }

    fun rentItem(rentalItemId: Int) {
        viewModelScope.launch {
            try {
                _rentalItemState.value = _rentalItemState.value.copy(loading = true)
                rentalItemsService.rentItem(
                    rentalItemId,
                    RentItemReq(
                        id = MainActivity.userId
                    )
                )
            } catch (e: Exception) {
                _rentalItemState.value = _rentalItemState.value.copy(err = e.toString())
            } finally {
                _rentalItemState.value = _rentalItemState.value.copy(loading = false)
            }
        }
    }

    fun clearErr() {
        _deleteRentalItemState.value = _deleteRentalItemState.value.copy(err = null)
    }

    fun verifyRentalItemDeletion(rentalItemId: Int) {
        _deleteRentalItemState.value = _deleteRentalItemState.value.copy(id = rentalItemId)
    }

    fun deleteRentalItem(rentalItemId: Int) {
        viewModelScope.launch {
            try {
                rentalItemsService.deleteItem(rentalItemId)
                val listOfItems = _rentalItemsState.value.list.filter {
                    rentalItemId != it.id
                }
                _rentalItemsState.value = _rentalItemsState.value.copy(list = listOfItems)
                _deleteRentalItemState.value = _deleteRentalItemState.value.copy(id = 0)
            } catch (e:Exception) {
                _deleteRentalItemState.value = _deleteRentalItemState.value.copy(err = e.toString())
            }
        }
    }

    fun toggleAddRentalItem() {
        _rentalItemsState.value =
            _rentalItemsState.value.copy(isAddingRentalItem = !_rentalItemsState.value.isAddingRentalItem)
    }

    fun setName(newName: String) {
        _addRentalItemState.value = _addRentalItemState.value.copy(name = newName)
    }

    fun addRentalItem() {
        viewModelScope.launch {
            try {
                _addRentalItemState.value = _addRentalItemState.value.copy(loading = true)
                val res = rentalItemsService.addItem(
                    _categoryId,
                    AddItemReq(
                        name = _addRentalItemState.value.name,
                        id = MainActivity.userId
                    )
                )
                _rentalItemsState.value = rentalItemsState.value.copy(list = _rentalItemsState.value.list + res)
                toggleAddRentalItem()
            } catch (e: Exception) {
                _addRentalItemState.value = _addRentalItemState.value.copy(err = e.toString())
            } finally {
                _addRentalItemState.value = _addRentalItemState.value.copy(loading = false)
            }
        }
    }
}