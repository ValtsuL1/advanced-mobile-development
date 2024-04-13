package com.example.edistynyt_mobiiliohjelmointi

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.edistynyt_mobiiliohjelmointi.viewmodel.CategoriesViewModel
import org.w3c.dom.Text

@Composable
fun RandomImage() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://picsum.photos/300")
            .build(),
        contentDescription = "random image"
    )
}

@Composable
fun AddCategoryDialog(
    addCategory: () -> Unit,
    name: String,
    setName: (String) -> Unit,
    closeDialog: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { closeDialog() }, confirmButton = {
            TextButton(onClick = { addCategory() }) {
                Text(text = "Save Category")
            }
        }, title = {
            Text(text = "Add Category")
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { newName ->
                    setName(newName)
                },
                placeholder = { Text(text = "Category Name") })
        })
}    

@Composable
fun ConfirmCategoryDelete(
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
        Text(text = "Are you sure you want to delete this category?")
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onMenuClick: () -> Unit,
    navigateToEditCategory: (Int) -> Unit,
    navigateToCategoryItems: (Int) -> Unit) {

    val vm: CategoriesViewModel = viewModel()
    
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.toggleAddCategory() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Category")
            }                   
        },
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
                vm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

                vm.categoriesState.value.err != null -> Text(text = "Error: ${vm.categoriesState.value.err}")

                vm.categoriesState.value.isAddingCategory -> AddCategoryDialog(
                    addCategory = { vm.createCategory() },
                        name = vm.addCategoryState.value.name,
                        setName = {newName -> vm.setName(newName)},
                        closeDialog = {vm.toggleAddCategory()}
                )

                vm.deleteCategoryState.value.id > 0 -> ConfirmCategoryDelete(
                    onConfirm = { vm.deleteCategoryById(vm.deleteCategoryState.value.id) },
                    onCancel = { vm.verifyCategoryRemoval(0) },
                    clearErr = { vm.clearErr() },
                    errStr = vm.deleteCategoryState.value.err
                )

                else -> LazyColumn() {
                    items(vm.categoriesState.value.list) {
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
                                        style = MaterialTheme.typography.headlineSmall,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    IconButton(onClick = { navigateToCategoryItems(it.id) }) {
                                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Items")
                                    }
                                    IconButton(onClick = { vm.verifyCategoryRemoval(it.id) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                                    }
                                    IconButton(onClick = { navigateToEditCategory(it.id) }) {
                                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
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
