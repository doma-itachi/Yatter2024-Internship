package com.dmm.bootcamp.yatter2024.ui.register

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class RegisterDestination: Destination(ROUTE) {
    companion object{
        private const val ROUTE = "register"

        fun createNode(builder: NavGraphBuilder){
            builder.composable(ROUTE){
                RegisterPage()
            }
        }
    }
}