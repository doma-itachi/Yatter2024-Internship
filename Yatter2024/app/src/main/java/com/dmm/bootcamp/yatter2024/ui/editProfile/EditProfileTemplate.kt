package com.dmm.bootcamp.yatter2024.ui.editProfile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme

@Composable
fun EditProfileTemplate(
    editBindingModel: EditBindingModel,
    isLoading: Boolean,
    onChangedDisplayName: (String) -> Unit,
    onChangedNote: (String) -> Unit,
    onChangedHeader: (String) -> Unit,
    onChangedAvatar: (String) -> Unit,
    onClickNavIcon: () -> Unit,
    onSubmit: () -> Unit,
    onClickIcon: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.editProfile_header_title))
                },
                navigationIcon = {
                    IconButton(onClick = onClickNavIcon) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.editProfile_header_back)
                        )
                    }
                }
            )
        }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ){
            Column(modifier = Modifier.fillMaxSize()) {
//                IconButton(onClick = onClickIcon) {
//                    val context = LocalContext.current
//
//                    val placeholder = ResourcesCompat.getDrawable(
//                        context.resources,
//                        R.drawable.avatar_placeholder,
//                        null,
//                    )
//                    AsyncImage(
//                        modifier = Modifier
//                            .size(48.dp)
//                            .clip(CircleShape),
//                        model = ImageRequest.Builder(context)
//                            .data(editBindingModel.avatar.toString())
//                            .placeholder(placeholder)
//                            .error(placeholder)
//                            .fallback(placeholder)
//                            .setHeader("User-Agent", "Mozilla/5.0")
//                            .build(),
//                        contentDescription = "アバター画像",
//                        contentScale = ContentScale.Crop,
//                    )
//                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = stringResource(id = R.string.editProfile_displayName)
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = editBindingModel.displayName,
                    onValueChange = onChangedDisplayName,
                    placeholder = {
                        Text(text = stringResource(id = R.string.editProfile_displayName))
                    }
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = stringResource(id = R.string.editProfile_note)
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = editBindingModel.note,
                    onValueChange = onChangedNote,
                    placeholder = {
                        Text(text = stringResource(id = R.string.editProfile_note))
                    }
                )

//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp),
//                    text = stringResource(id = R.string.editProfile_avatar)
//                )
//                OutlinedTextField(
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 16.dp),
//                    value = editBindingModel.avatar,
//                    onValueChange = onChangedAvatar,
//                    placeholder = {
//                        Text(text = stringResource(id = R.string.editProfile_avatar))
//                    }
//                )
//
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp),
//                    text = stringResource(id = R.string.editProfile_header)
//                )
//                OutlinedTextField(
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 16.dp),
//                    value = editBindingModel.header,
//                    onValueChange = onChangedHeader,
//                    placeholder = {
//                        Text(text = stringResource(id = R.string.editProfile_header))
//                    }
//                )
                Button(onClick = onSubmit) {
                    Text(text = stringResource(id = R.string.editProfile_submit))
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileTemplatePreview(){
    Yatter2024Theme {
        Surface {
            EditProfileTemplate(
                editBindingModel = EditBindingModel(
                    displayName = "ユーザー名",
                    note = "こんにちは",
                    avatar = "",
                    header = ""
                ),
                isLoading = false,
                onChangedDisplayName = {},
                onChangedNote = {},
                onChangedHeader = {},
                onChangedAvatar = {},
                onClickNavIcon = {},
                onSubmit = {},
                onClickIcon = {}
            )
        }
    }
}