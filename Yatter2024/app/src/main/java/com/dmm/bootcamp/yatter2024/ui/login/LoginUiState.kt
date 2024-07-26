package com.dmm.bootcamp.yatter2024.ui.login

data class LoginUiState(
    val loginBindingModel: LoginBindingModel,
    val isLoading: Boolean,
    val validUserName: Boolean,
    val validPassword: Boolean
){
    val isEnableLogin: Boolean = validUserName && validPassword

    companion object {
        fun empty(): LoginUiState = LoginUiState(
            loginBindingModel = LoginBindingModel(
                username = "",
                password = ""
            ),
            validUserName = false,
            validPassword = false,
            isLoading = false
        )
    }
}
