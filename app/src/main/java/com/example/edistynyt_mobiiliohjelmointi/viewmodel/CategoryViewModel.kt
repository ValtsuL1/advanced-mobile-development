package com.example.edistynyt_mobiiliohjelmointi.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyt_mobiiliohjelmointi.api.categoriesService
import com.example.edistynyt_mobiiliohjelmointi.model.CategoryState
import com.example.edistynyt_mobiiliohjelmointi.model.EditCategoryReq
import kotlinx.coroutines.launch

class CategoryViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    fun setOk(status: Boolean) {
        _categoryState.value = _categoryState.value.copy(ok = status)
    }

    fun editCategory() {
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                categoriesService.editCategory(
                    _categoryId,
                    EditCategoryReq(name = _categoryState.value.item.name)
                )
                setOk(true)
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(err = e.toString())
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }

    private fun getCategory() {
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                val res = categoriesService.getCategory(_categoryId)
                _categoryState.value = _categoryState.value.copy(item = res.category)
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(err = e.toString())
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }

    init {
        getCategory()
    }

    fun setName(newName: String) {
        val item = _categoryState.value.item.copy(name = newName)
        _categoryState.value = _categoryState.value.copy(item = item)
    }

    fun editCategory(goToCategories: () -> Unit) {
        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                categoriesService.editCategory(
                    _categoryId,
                    EditCategoryReq(name = _categoryState.value.item.name)
                )
                goToCategories()
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(err = e.toString())
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }
}