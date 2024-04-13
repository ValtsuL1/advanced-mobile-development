package com.example.edistynyt_mobiiliohjelmointi

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.edistynyt_mobiiliohjelmointi.viewmodel.RentalItemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemsScreen(backToCategories: () -> Unit) {
    val vm: RentalItemsViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Items") },
                navigationIcon = {
                    IconButton(onClick = { backToCategories() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back To Categories")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            when {
                vm.rentalItemsState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                vm.rentalItemsState.value.err != null -> Text(text = "Error: ${vm.rentalItemsState.value.err}")

                else -> LazyColumn(){
                    items(vm.rentalItemsState.value.list) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RandomImage()
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
