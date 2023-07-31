package com.andreisingeleytsev.vulkanapp.ui.screens.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreisingeleytsev.vulkanapp.ui.utils.Routes
import com.andreisingeleytsev.vulkanapp.ui.screens.game_screen.GameScreen
import com.andreisingeleytsev.vulkanapp.ui.screens.main_screen.MainScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun VulkanNavigation() {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.GAME_SCREEN) {
            GameScreen(navHostController)
        }
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navHostController = navHostController)
        }
    }
}