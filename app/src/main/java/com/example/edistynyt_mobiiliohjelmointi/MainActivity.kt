package com.example.edistynyt_mobiiliohjelmointi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edistynyt_mobiiliohjelmointi.ui.theme.Edistynyt_mobiiliohjelmointiTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Edistynyt_mobiiliohjelmointiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    ModalNavigationDrawer(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))

                                NavigationDrawerItem(
                                    label = { Text(text = "Categories") },
                                    selected = true,
                                    onClick = {

                                        scope.launch {
                                            navController.navigate("categoriesScreen")
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home"
                                        )
                                    }
                                )

                                NavigationDrawerItem(
                                    label = { Text(text = "Login") },
                                    selected = true,
                                    onClick = {

                                        scope.launch {
                                            navController.navigate("loginScreen")
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Login"
                                        )
                                    }
                                )
                            }
                        }, drawerState = drawerState
                    ){
                        NavHost(
                            navController = navController,
                            startDestination = "categoriesScreen"
                            ){

                            composable("categoriesScreen") {
                                CategoriesScreen(onMenuClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }, navigateToEditCategory = {
                                    navController.navigate("editCategoryScreen/${it}")
                                })
                            }

                            composable("editCategoryScreen/{categoryId}") {

                                EditCategoryScreen(backToCategories = {
                                    navController.navigateUp()
                                }, goToCategories = {
                                    navController.navigate("categoriesScreen")
                                })

                                EditCategoryScreen(backToCategories = {
                                    navController.navigateUp()
                                }, goToCategories = {
                                    navController.navigate("categoriesScreen")
                                })
                            }

                            composable("loginScreen") {
                                LoginScreen(goToCategories = {
                                    scope.launch {
                                        navController.navigate("categoriesScreen")
                                    }
                                })
                            }

                            composable("postsScreen") {
                                PostsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
