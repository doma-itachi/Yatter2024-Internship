package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@Composable
fun RegisterTemplate(
    username: String,
    onChangedUsername: (String) -> Unit,
    password: String,
    onChangedPassword: (String) -> Unit,
    isEnableRegister: Boolean,
    isLoading: Boolean,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit
) {
    Scaffold (
        topBar = {
            TopAppBar (
                title = {
                    Text(text = stringResource(id = R.string.register_header_title))
                }
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp),
        ){
            Column(modifier = Modifier.fillMaxSize()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = stringResource(id = R.string.register_form_username)
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = username,
                    onValueChange = onChangedUsername,
                    placeholder = {
                        Text(text = stringResource(id = R.string.register_form_username_placeholder))
                    }
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.register_form_password)
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = password,
                    onValueChange = onChangedPassword,
                    placeholder = {
                        Text(text = stringResource(id = R.string.register_form_password_placeholder))
                    }
                )

                Button(
                    enabled = isEnableRegister,
                    onClick = onClickRegister,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(text = stringResource(id = R.string.register_form_submit))
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = stringResource(id = R.string.register_if_registered),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2,
                )
                TextButton(
                    onClick = onClickLogin,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(text = stringResource(id = R.string.register_login))
                }
            }

            if(isLoading){
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun RegisterTemplatePreview(){
    Yatter2024Theme {
        Surface {
            RegisterTemplate(
                username = "username",
                onChangedUsername = {},
                password = "password",
                onChangedPassword = {},
                isEnableRegister = true,
                isLoading = false,
                onClickRegister = {},
                onClickLogin = {}
            )
        }
    }
}