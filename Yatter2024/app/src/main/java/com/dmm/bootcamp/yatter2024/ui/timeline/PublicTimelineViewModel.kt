package com.dmm.bootcamp.yatter2024.ui.timeline

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.editProfile.EditProfileDestination
import com.dmm.bootcamp.yatter2024.ui.post.PostDestination
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter.AccountConverter
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicTimelineViewModel(
    private val statusRepository: StatusRepository,
    private val accountRepository: AccountRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<PublicTimelineUiState> = MutableStateFlow(PublicTimelineUiState.empty())
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private suspend fun fetchPublicTimeline(){
        val statusList = statusRepository.findAllPublic()

        _uiState.update {
            it.copy(
                statusList = StatusConverter.convertToBindingModel(statusList)
            )
        }
    }

    private suspend fun fetchAccountInfo(){
        val me = accountRepository.findMe() ?: return

        _uiState.update {
            it.copy(
                account = AccountConverter.convertToBindingModel(me)
            )
        }
    }

    fun onResume(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchPublicTimeline()
            fetchAccountInfo()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onRefresh(){
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            fetchPublicTimeline()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    fun onClickPost(){
        _destination.value = PostDestination()
    }

    fun onClickProfile(){
        if(uiState.value.account != null)
            _destination.value = ProfileDestination(uiState.value.account!!.username)
    }
    fun onClickLogout(){

    }

    fun onClickIcon(username: String){
        _destination.value = ProfileDestination(username)
    }

    fun onEditProfile(){
        _destination.value = EditProfileDestination()
    }

    fun onCompleteNavigation(){
        _destination.value = null
    }
}