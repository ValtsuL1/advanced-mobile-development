package com.example.edistynyt_mobiiliohjelmointi.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edistynyt_mobiiliohjelmointi.api.postsService
import com.example.edistynyt_mobiiliohjelmointi.model.PostsState
import kotlinx.coroutines.launch

class PostsViewModel: ViewModel() {
    private val _postsState = mutableStateOf(PostsState())
    val postsState: State<PostsState> = _postsState

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            try {
                _postsState.value = _postsState.value.copy(loading = true)
                val response = postsService.getPosts()
                _postsState.value = _postsState.value.copy(list = response)
            } catch (e: Exception) {
                _postsState.value = _postsState.value.copy(err = e.toString())
            } finally {
                _postsState.value = _postsState.value.copy(loading = false)
            }

        }
    }
}