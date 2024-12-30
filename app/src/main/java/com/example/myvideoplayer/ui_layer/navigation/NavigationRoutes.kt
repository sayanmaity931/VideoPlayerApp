package com.example.myvideoplayer.ui_layer.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class PlayerScreen(val videoUri : String , val title : String? = null )

@Serializable
object SplashScreen