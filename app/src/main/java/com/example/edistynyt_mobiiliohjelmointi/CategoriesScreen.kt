package com.example.edistynyt_mobiiliohjelmointi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyt_mobiiliohjelmointi.viewmodel.CategoriesViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(onMenuClick: () -> Unit) {
    val categoriesVm: CategoriesViewModel = viewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Categories") },
                navigationIcon = {
                    IconButton(onClick = { onMenuClick() }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                })
    }) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ){
            when {
                categoriesVm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
                else -> LazyColumn() {
                    items(categoriesVm.categoriesState.value.list) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Kuva tähän myöhemmin")
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = it.name, fontWeight = FontWeight.ExtraBold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}