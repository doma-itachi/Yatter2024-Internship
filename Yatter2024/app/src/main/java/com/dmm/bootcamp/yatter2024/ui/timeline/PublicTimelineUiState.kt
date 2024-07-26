package com.dmm.bootcamp.yatter2024.ui.timeline

import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.AccountBindingModel
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.StatusBindingModel

data class PublicTimelineUiState(
    val statusList: List<StatusBindingModel>,
    val account: AccountBindingModel?,
    val isLoading: Boolean,
    val isRefreshing: Boolean
){
    companion object{
        fun empty(): PublicTimelineUiState = PublicTimelineUiState(
            statusList = emptyList(),
            account = null,
            isLoading = false,
            isRefreshing = false
        )
    }
}
