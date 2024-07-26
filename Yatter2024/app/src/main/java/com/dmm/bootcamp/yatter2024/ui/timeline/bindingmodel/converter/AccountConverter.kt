package com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter

import com.dmm.bootcamp.yatter2024.domain.model.Me
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.AccountBindingModel

object AccountConverter {
    fun convertToBindingModel(me: Me): AccountBindingModel = AccountBindingModel(
        id = me.id.value,
        username = me.username.value,
        displayName = me.displayName?:"",
        avatar = me.avatar?.toString(),
        followerCount = me.followerCount,
        followingCount = me.followingCount
    )

}