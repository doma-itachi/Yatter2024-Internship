package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.ui.editProfile.EditProfilePage
import java.lang.StringBuilder

class ProfileDestination(
    private val username: String
): Destination(ROUTE) {
    companion object{
        private val ROUTE = "profile/{username}"

        fun createNode(builder: NavGraphBuilder){
            builder.composable(
                ROUTE,
                listOf(navArgument("username"){type = NavType.StringType}),
                enterTransition = { slideInHorizontally(initialOffsetX = {screenWidth -> screenWidth }, animationSpec = tween()) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { screenWidth -> screenWidth}, animationSpec = tween())}
            ){ backStackEntry: NavBackStackEntry ->
                ProfilePage(username = backStackEntry.arguments?.getString("username")?:"")
            }
        }
        fun genRoute(username: String): String{
            return "profile/$username"
        }
    }
    override fun buildRoute(): String{
        return genRoute(username)
    }
}