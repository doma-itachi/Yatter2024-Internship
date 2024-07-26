package com.dmm.bootcamp.yatter2024.ui.profile

import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

data class ProfileUiState (
    val statusList: List<StatusBindingModel>,
    val account: AccountBindingModel?,
    val isLoading: Boolean,
    val isRefreshing: Boolean
){
    companion object {
        fun empty(): ProfileUiState = ProfileUiState(
            statusList = emptyList(),
            account = null,
            isLoading = false,
            isRefreshing = false
        )
    }
}