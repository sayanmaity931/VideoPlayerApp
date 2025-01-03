package com.example.myvideoplayer.ui_layer.navigation

import com.example.myvideoplayer.data_layer.VideoFile
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class PlayerScreen(val videoUri : String , val title : String? = null )

@Serializable
object SplashScreen

@Serializable
data class VideosInFolderScreen(val folderName : String)