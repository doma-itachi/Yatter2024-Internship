package com.dmm.bootcamp.yatter2024.ui.editProfile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Route
import java.lang.StringBuilder
import java.net.URL

class EditProfileViewModel(
    private val accountRepository: AccountRepository,
    private val getMeService: GetMeService
): ViewModel() {
    private val _uiState: MutableStateFlow<EditProfileUiState> = MutableStateFlow(EditProfileUiState.empty())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private val _pickedImageUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val pickedImageUri: StateFlow<Uri> = _pickedImageUri.asStateFlow()

    fun onCreate(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val me = getMeService.execute()

            val snapshotBindingModel = uiState.value.bindingModel
            _uiState.update {
                it.copy(
                    bindingModel = snapshotBindingModel.copy(
                        displayName = me?.displayName?:"",
                        note = me?.note?:"",
                        header = me?.header?.toString()?:"",
                        avatar = me?.avatar?.toString()?:""
                    ),
                    isLoading = false
                )
            }
        }
    }
    fun onChangedDisplayName(displayName: String){
        _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(displayName = displayName)) }
    }
    fun onChangedNote(note: String){
        _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(note = note)) }
    }
    fun onChangedHeader(header: String){
        _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(header = header)) }
    }
    fun onChangedAvatar(avatar: String){
        _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(avatar = avatar)) }
    }

    fun onChangedPickedImageUri(uri: Uri){
        _pickedImageUri.update { uri }
    }
    fun onClickNavIcon(){
        _destination.value = PopBackDestination
    }

    fun onCompleteNavigation(){
        _destination.value = null
    }



    fun onClickIcon(){
//val pickMedia = ActivityResultLauncher

    }

    fun onSubmit(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            Log.d("ittt", "Meの取得開始")
            val me = getMeService.execute()?:return@launch
            Log.d("ittt", "アップデート開始")
            accountRepository.update(
                me = me,
                newDisplayName = uiState.value.bindingModel.displayName,
                newNote = uiState.value.bindingModel.note,
                newAvatar = null,
                newHeader = null
//                newAvatar = URL(uiState.value.bindingModel.avatar),
//                newHeader = URL(uiState.value.bindingModel.header)
            )
            Log.d("ittt", "アップデート終了")
            _destination.value = PublicTimelineDestination()
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}