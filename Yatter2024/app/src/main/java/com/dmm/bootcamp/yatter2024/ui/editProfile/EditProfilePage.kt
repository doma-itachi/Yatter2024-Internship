package com.dmm.bootcamp.yatter2024.ui.editProfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun EditProfilePage(
    viewModel: EditProfileViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.navigate(navController)
        viewModel.onCompleteNavigation()
    }
    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
        viewModel.onCreate()
    }

    val pickedImageUri by viewModel.pickedImageUri.collectAsStateWithLifecycle()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onChangedPickedImageUri(it)
        }
    }
    fun onSelectImage() {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        viewModel.onClickIcon()
    }

    EditProfileTemplate(
        editBindingModel = uiState.bindingModel,
        isLoading = uiState.isLoading,
        onChangedDisplayName = viewModel::onChangedDisplayName,
        onChangedNote = viewModel::onChangedNote,
        onChangedAvatar = viewModel::onChangedAvatar,
        onChangedHeader = viewModel::onChangedHeader,
        onClickNavIcon = viewModel::onClickNavIcon,
        onSubmit = viewModel::onSubmit,
        onClickIcon = {onSelectImage()}
    )
}