package com.example.edistynyt_mobiiliohjelmointi.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyt_mobiiliohjelmointi.api.categoriesService
import com.example.edistynyt_mobiiliohjelmointi.model.AddCategoryReq
import com.example.edistynyt_mobiiliohjelmointi.model.AddCategoryState
import com.example.edistynyt_mobiiliohjelmointi.model.CategoriesState
import com.example.edistynyt_mobiiliohjelmointi.model.CategoryItem
import com.example.edistynyt_mobiiliohjelmointi.model.DeleteCategoryState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoriesViewModel: ViewModel() {

    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState

    private val _deleteCategoryState = mutableStateOf(DeleteCategoryState())
    val deleteCategoryState: State<DeleteCategoryState> = _deleteCategoryState

    private val  _addCategoryState = mutableStateOf(AddCategoryState())
    val addCategoryState: State<AddCategoryState> = _addCategoryState

    init {
        getCategories()
    }

    fun createCategory() {
        viewModelScope.launch {
            try {
                _addCategoryState.value = _addCategoryState.value.copy(loading = true)
                val res = categoriesService.createCategory(
                    AddCategoryReq(
                        name = _addCategoryState.value.name
                    )
                )
                _categoriesState.value = categoriesState.value.copy(list = _categoriesState.value.list + res)
                toggleAddCategory()
            } catch (e: Exception) {
                _addCategoryState.value = _addCategoryState.value.copy(err = e.toString())
            } finally {
                _addCategoryState.value = _addCategoryState.value.copy(loading = false)
            }
        }
    }

    fun setName(newName: String) {
        _addCategoryState.value = _addCategoryState.value.copy(name = newName)
    }

    fun toggleAddCategory() {
        _categoriesState.value =
            _categoriesState.value.copy(isAddingCategory = !_categoriesState.value.isAddingCategory)
    }

    fun clearErr() {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(err = null)
    }

    fun verifyCategoryRemoval(categoryId: Int) {
        _deleteCategoryState.value = _deleteCategoryState.value.copy(id = categoryId)
    }

    fun deleteCategoryById(categoryId: Int) {
        viewModelScope.launch {
            try {
                categoriesService.removeCategory(categoryId)
                val listOfCategories = _categoriesState.value.list.filter {
                    categoryId != it.id
                }
                _categoriesState.value = _categoriesState.value.copy(list = listOfCategories)
                _deleteCategoryState.value = _deleteCategoryState.value.copy(id = 0)
            } catch (e: Exception) {
                _deleteCategoryState.value = _deleteCategoryState.value.copy(err = e.toString())
            } finally {

            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
              _categoriesState.value = _categoriesState.value.copy(loading = true)
              val res = categoriesService.getCategories()
              _categoriesState.value = categoriesState.value.copy(list = res.categories)
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(err = e.toString())
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }
    }
}