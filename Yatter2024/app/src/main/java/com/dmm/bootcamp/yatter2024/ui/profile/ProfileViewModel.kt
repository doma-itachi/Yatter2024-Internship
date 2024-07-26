package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter.AccountConverter
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val statusRepository: StatusRepository,
    private val accountRepository: AccountRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.empty())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _username: MutableStateFlow<String?> = MutableStateFlow(null);

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private suspend fun fetchAccount(){
        if(_username.value==null)return

        val account = accountRepository.findByUsername(Username(_username.value!!))?:return
        _uiState.update {
            it.copy(
                account = AccountBindingModel(
                    username = account.username.value,
                    displayName = account.displayName,
                    note = account.note,
                    avatar = account.avatar,
                    header = account.header,
                    followingCount = account.followingCount,
                    followerCount = account.followerCount
                )
            )
        }
    }

    private suspend fun fetchTimeline(){
        val statusList = statusRepository.findAllPublic()
        val filteredStatusList = statusList.filter {
            it.account.username.value == _username.value
        }
        _uiState.update {
            it.copy(
                statusList = StatusConverter.convertToBindingModel(filteredStatusList)
            )
        }
    }

    fun onCreate(username: String){
        _username.value = username
    }
    fun onResume(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchAccount()
            fetchTimeline()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onRefresh(){
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            fetchTimeline()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
    fun onClickNav(){
        _destination.value = PopBackDestination
    }

    fun onCompleteNavigation(){
        _destination.value = null
    }
}