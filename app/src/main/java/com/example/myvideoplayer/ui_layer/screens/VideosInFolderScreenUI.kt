package com.example.myvideoplayer.ui_layer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myvideoplayer.ui_layer.navigation.PlayerScreen
import com.example.myvideoplayer.ui_layer.view_model.MyViewModel

@Composable
fun VideoPlayerScreenUI(
    modifier: Modifier = Modifier,
    navController: NavController,
    folderName: String?
) {

    val viewModel = hiltViewModel<MyViewModel>()
    val videoFolderState = viewModel.getVideoByFolder.collectAsStateWithLifecycle()

    if (videoFolderState.value.isLoading) {
        CircularProgressIndicator()
    } else if (!videoFolderState.value.error.isNullOrEmpty()) {
        Text(text = videoFolderState.value.error!!)
    } else if (videoFolderState.value.data.isEmpty()) {
        Text(text = "No Videos Found")
    } else {

        val videosInFolder = folderName?.let { videoFolderState.value.data[it] }

        if (videosInFolder.isNullOrEmpty()){
            Text(text = "No videos in folder $folderName")
        }else {

            LazyColumn (modifier = Modifier.fillMaxSize()){

                items(videosInFolder){ videoFile ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        shape = CardDefaults.elevatedShape,
                        colors = CardDefaults.cardColors(Color(0xFF431693)),
                        onClick = {
                            navController.navigate(PlayerScreen(videoUri = videoFile.path.toString() , title = videoFile.fileName))
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = videoFile.fileName)
                        }
                    }
                }
            }
        }
    }
}