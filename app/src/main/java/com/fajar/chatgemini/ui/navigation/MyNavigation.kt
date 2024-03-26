package com.fajar.chatgemini.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fajar.chatgemini.ui.data.MainViewModel
import com.fajar.chatgemini.ui.screen.ImageChatScreen
import com.fajar.chatgemini.ui.screen.MultiTurnScreen
import com.fajar.chatgemini.ui.screen.SetApiScreen
import com.fajar.chatgemini.ui.screen.SettingScreen
import com.fajar.chatgemini.ui.screen.SingleTurnScreen

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun MyNavigation(
    viewModel: MainViewModel,
    startDestination: String = SingleTurnMode.route
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination)
    {
        composable(MultiTurnMode.route) {
            MultiTurnScreen(viewModel, navController)
        }
        composable(ImageMode.route) {
            ImageChatScreen(viewModel, navController)
        }
        composable(SingleTurnMode.route)
        {
            SingleTurnScreen(viewModel, navController)
        }
        composable(Settings.route) {
            SettingScreen(navController)
        }
        composable(SetApi.route) {
            SetApiScreen(viewModel, navController)
        }

    }
}