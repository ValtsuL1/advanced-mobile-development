package com.example.edistynyt_mobiiliohjelmointi.view

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyt_mobiiliohjelmointi.MainActivity
import com.example.edistynyt_mobiiliohjelmointi.viewmodel.RentalItemsViewModel

@Composable
fun AddRentalItemDialog(
   addRentalItem: () -> Unit,
   name: String,
   setName: (String) -> Unit,
   closeDialog: () -> Unit
) {
    AlertDialog(onDismissRequest = { closeDialog() }, confirmButton = {
        TextButton(onClick = { addRentalItem() }) {
            Text(text = "Save Item")
        }
    }, title = {
        Text(text = "Add item")    
    }, text = {
        OutlinedTextField(
            value = name,
            onValueChange = {newName ->
                setName(newName)
            },
            placeholder = { Text(text = "Item name")})
    })
}

@Composable
fun ConfirmRentalItemDelete(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    clearErr: () -> Unit,
    errStr: String?
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = errStr) {
        errStr?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            clearErr()
        }
    }

    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton =  {
        TextButton(onClick = { onConfirm() }) {
            Text(text = "Delete")
        }
    }, dismissButton = {
        TextButton(onClick = { onCancel() }) {
            Text(text = "Cancel")
        }
    }, title = {
        Text(text = "Are you sure?")
    }, text = {
        Text(text = "Are you sure you want to delete this item?")
    })
}

@Composable
fun ConfirmItemRental(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    clearErr: () -> Unit,
    errStr: String?
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = errStr) {
        errStr?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            clearErr()
        }
    }

    AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton =  {
        TextButton(onClick = { onConfirm() }) {
            Text(text = "Rent")
        }
    }, dismissButton = {
        TextButton(onClick = { onCancel() }) {
            Text(text = "Cancel")
        }
    }, title = {
        Text(text = "Are you sure?")
    }, text = {
        Text(text = "Are you sure you want to rent this item?")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemsScreen(
    backToCategories: () -> Unit,
    navigateToEditItem: (Int) -> Unit
) {
    val vm: RentalItemsViewModel = viewModel()
    val isLoggedIn = MainActivity.isLoggedIn

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.toggleAddRentalItem() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add item")
            }
        },
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

                vm.deleteRentalItemState.value.id > 0 -> ConfirmRentalItemDelete(
                    onConfirm = { vm.deleteRentalItem(vm.deleteRentalItemState.value.id) },
                    onCancel = { vm.verifyRentalItemDeletion(0) },
                    clearErr = { vm.clearDeleteErr() },
                    errStr = vm.deleteRentalItemState.value.err
                )
                
                vm.rentalItemsState.value.isAddingRentalItem -> AddRentalItemDialog(
                    addRentalItem = { vm.addRentalItem() },
                    name = vm.addRentalItemState.value.name,
                    setName = {newName -> vm.setName(newName)},
                    closeDialog = {vm.toggleAddRentalItem()}
                )
                
                vm.rentItemState.value.id > 0 -> ConfirmItemRental(
                    onConfirm = { vm.rentItem(vm.rentItemState.value.id) },
                    onCancel = { vm.verifyItemRental(0) },
                    clearErr = { vm.clearRentErr() },
                    errStr = vm.rentItemState.value.err
                )
                
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
                                    IconButton(onClick = { vm.verifyItemRental(it.id) }) {
                                        Icon(imageVector = Icons.Default.Add, contentDescription = "Rent Item")
                                    }
                                    IconButton(enabled = isLoggedIn,
                                        onClick = { navigateToEditItem(it.id) }) {
                                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Item")
                                    }
                                    IconButton(enabled = isLoggedIn,
                                        onClick = { vm.verifyRentalItemDeletion(it.id) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
