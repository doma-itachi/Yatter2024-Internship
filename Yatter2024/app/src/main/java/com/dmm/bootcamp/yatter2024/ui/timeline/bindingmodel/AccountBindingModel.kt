package com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel

data class AccountBindingModel (
    val id: String,
    val username: String,
    val displayName: String?,
    val avatar: String?,
    val followingCount: Int,
    val followerCount: Int
)