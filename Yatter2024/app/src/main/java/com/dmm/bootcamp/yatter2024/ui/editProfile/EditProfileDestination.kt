package com.dmm.bootcamp.yatter2024.ui.editProfile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class EditProfileDestination: Destination(ROUTE) {
    companion object{
        private const val ROUTE = "editProfile"

        fun createNode(builder: NavGraphBuilder){
            builder.composable(ROUTE){
                EditProfilePage()
            }
        }
    }
}