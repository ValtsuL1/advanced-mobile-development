package com.example.edistynyt_mobiiliohjelmointi.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyt_mobiiliohjelmointi.viewmodel.RentalItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRentalItemScreen(backToItems: () -> Unit, goToItems: (Int) -> Unit) {
    val vm: RentalItemViewModel = viewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = vm.rentalItemState.value.item.name) },
                navigationIcon = {
                    IconButton(onClick = { backToItems() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back To Items"
                        )
                    }
                })
        }
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                vm.rentalItemState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                vm.rentalItemState.value.err != null -> Text(text = "Error ${vm.rentalItemState.value.err}")

                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = vm.rentalItemState.value.item.name,
                            onValueChange = { vm.setName(it) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { vm.editRentalItem(goToItems) }) {
                            Text(text = "Edit Name")
                        }
                    }
                }
            }
        }
    }
}