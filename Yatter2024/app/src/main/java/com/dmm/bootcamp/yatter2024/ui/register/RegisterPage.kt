package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterPage(
    viewModel: RegisterAccountViewModel = getViewModel()
) {
    val uiState: RegisterUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.let {
            it.navigate(navController)
            viewModel.onCompleteNavigation()
        }
    }

    RegisterTemplate(
        username = uiState.registerBindingModel.username,
        onChangedUsername = viewModel::onChangedUsername,
        password = uiState.registerBindingModel.password,
        onChangedPassword = viewModel::onChangedPassword,
        isEnableRegister = uiState.isEnableRegister,
        isLoading = uiState.isLoading,
        onClickRegister = viewModel::onClickRegister,
        onClickLogin = viewModel::onClickLogin
        )
}