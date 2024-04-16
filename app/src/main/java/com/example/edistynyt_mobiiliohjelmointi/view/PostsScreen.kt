package com.example.edistynyt_mobiiliohjelmointi.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyt_mobiiliohjelmointi.viewmodel.PostsViewModel

@Composable
fun PostsScreen() {
    val vm: PostsViewModel = viewModel()
    LazyColumn() {
        items(vm.postsState.value.list) {
            Text(text = it.title)
        }
    }
}