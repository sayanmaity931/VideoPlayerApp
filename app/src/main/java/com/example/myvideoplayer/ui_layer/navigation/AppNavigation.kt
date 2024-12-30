package com.example.myvideoplayer.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myvideoplayer.SplashScreenActivity
import com.example.myvideoplayer.ui_layer.screens.ExoPlayerScreenUI
import com.example.myvideoplayer.ui_layer.screens.HomeScreenUI
import com.example.myvideoplayer.ui_layer.screens.SplashScreenUi

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashScreen) {

        composable<HomeScreen>{
            HomeScreenUI(navController= navController)
        }
        composable<PlayerScreen>{
            val videoUri: PlayerScreen = it.toRoute<PlayerScreen>()
            ExoPlayerScreenUI(videoUri = videoUri.videoUri)
        }
        composable<SplashScreen>{
            SplashScreenUi(navController = navController)
        }
    }
}