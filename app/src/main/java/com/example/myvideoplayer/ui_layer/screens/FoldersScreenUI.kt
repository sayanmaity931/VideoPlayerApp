package com.example.myvideoplayer.ui_layer.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.myvideoplayer.ui_layer.navigation.VideosInFolderScreen
import com.example.myvideoplayer.ui_layer.view_model.MyViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun FoldersScreenUI(modifier: Modifier = Modifier , navController: NavController) {

        val viewModel = hiltViewModel<MyViewModel>()
        val videoFolder = viewModel.getVideoByFolder.collectAsStateWithLifecycle()

        if (videoFolder.value.isLoading) {
                CircularProgressIndicator()
        } else if (!videoFolder.value.error.isNullOrEmpty()) {
                Text(text = videoFolder.value.error!!)
        } else if (videoFolder.value.data.isEmpty()) {
                Text(text = "No Folders Found")
        } else {
                LazyColumn (
                        modifier = Modifier.fillMaxSize()
                ){
                        items(videoFolder.value.data.keys.toList()) {
                                Card (
                                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp)
                                                .size(75.dp),
                                        elevation = CardDefaults.cardElevation(6.dp),
                                        shape = CardDefaults.elevatedShape,
                                        colors = CardDefaults.cardColors(Color(0xFF431693)),
                                        onClick = {
                                                navController.navigate(VideosInFolderScreen(folderName = it))                                        }
                                ){
                                        Column (
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                        ){
                                                Text(text = it)
                                        }
                                }
                        }
                }
        }
}