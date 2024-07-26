package com.dmm.bootcamp.yatter2024.ui.profile

import java.net.URL

data class AccountBindingModel (
    val username: String,
    val displayName: String?,
    val note: String?,
    val avatar: URL?,
    val header: URL?,
    val followingCount: Int,
    val followerCount: Int
)