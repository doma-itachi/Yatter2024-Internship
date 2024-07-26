package com.dmm.bootcamp.yatter2024.ui.editProfile

import com.dmm.bootcamp.yatter2024.domain.model.Me

data class EditBindingModel (
    val displayName: String,
    val note: String,
    val avatar: String,
    val header: String
)