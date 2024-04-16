package com.example.edistynyt_mobiiliohjelmointi.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynyt_mobiiliohjelmointi.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(goToLogin: () -> Unit) {
    val vm: LoginViewModel = viewModel()

    Box(){
        when {
            vm.loginState.value.loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
            else -> Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = vm.loginState.value.username,
                    onValueChange = { username ->
                        vm.setUsername(username)
                    },
                    placeholder = {
                        Text(text = "Username")
                    })
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = vm.loginState.value.password,
                    onValueChange = { password ->
                        vm.setPassword(password)
                    },
                    placeholder = {
                        Text(text = "Password")
                    },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    enabled = vm.loginState.value.username != "" && vm.loginState.value.password != "",
                    onClick = {
                        vm.register(goToLogin)
                    }
                ) {
                    Text(text = "Register")
                }
            }
        }
    }
}