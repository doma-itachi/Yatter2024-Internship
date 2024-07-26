package com.dmm.bootcamp.yatter2024.ui.editProfile

data class EditProfileUiState (
    val bindingModel: EditBindingModel,
    val isLoading: Boolean
){
    companion object {
        fun empty(): EditProfileUiState = EditProfileUiState(
            bindingModel = EditBindingModel(
                displayName = "",
                note = "",
                header = "",
                avatar = ""
            ),
            isLoading = false
        )
    }
}