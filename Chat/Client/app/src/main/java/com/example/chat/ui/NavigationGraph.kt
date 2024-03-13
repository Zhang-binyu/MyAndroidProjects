package com.example.chat.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat.ui.pages.LobbyScreen
import com.example.chat.ui.pages.WelcomePage
import com.example.chat.viewmodel.MainViewModel

@Composable
fun NavigationGraph(
    mainViewModel: MainViewModel
) {
    val navigationController = rememberNavController()
    
    NavHost(navController = navigationController, startDestination = "WelcomePage"){
        composable(route = "WelcomePage"){
            WelcomePage(
                navigationController = navigationController,
                mainViewModel = mainViewModel
            )
        }

        composable(route = "LobbyScreen"){
            LobbyScreen(
                navigationController = navigationController,
                mainViewModel = mainViewModel
            )
        }


    }


}