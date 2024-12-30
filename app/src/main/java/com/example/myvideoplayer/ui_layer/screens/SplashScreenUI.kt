package com.example.myvideoplayer.ui_layer.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myvideoplayer.ui_layer.navigation.HomeScreen

@Composable
fun SplashScreenUi(modifier: Modifier = Modifier , navController: NavController) {
    Box (
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()

    }

    Log.d("TAG","Hi I am in SplashScreen")
    navController.navigate(HomeScreen)
}