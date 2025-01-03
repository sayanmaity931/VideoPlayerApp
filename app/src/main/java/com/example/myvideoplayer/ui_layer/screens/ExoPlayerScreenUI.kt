package com.example.myvideoplayer.ui_layer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@Composable
fun ExoPlayerScreenUI(videoUri : String) {

    val context = LocalContext.current
    val exoPlayer = remember{
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
            playWhenReady = true
            play()
        }
    }

    Column {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            update = {
                it.player = exoPlayer
            }
        )
        DisposableEffect(Unit){
            onDispose {
                exoPlayer.release()
            }
        }
    }
}


