package com.example.myvideoplayer.ui_layer.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myvideoplayer.State2
import com.example.myvideoplayer.ui_layer.navigation.PlayerScreen
import com.example.myvideoplayer.ui_layer.view_model.GetAllVideosState
import com.example.myvideoplayer.ui_layer.view_model.MyViewModel
import kotlin.toString


@Composable
fun VideosScreenUI(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {


    val allVideos = viewModel.getAllVideos.collectAsState()
    Log.d("TAG", "VideosScreenUI")

    /*  if (allVideos.value.data.isEmpty()) {
        Text(text = "No Videos Found")
    }
    else {
        Log.d("TAG", "It means You are IN")
        if (allVideos.value.data.isNotEmpty()) {
            when {

                allVideos.value.isLoading -> {
                    CircularProgressIndicator()
                    Log.d("TAG", "VideoScreenUI is Loading")
                }

                allVideos.value.error != null -> {
                    Text(text = allVideos.value.error.toString())
                    Log.d("TAG", allVideos.value.error + "VideoScreenUI is Error")
                }

                allVideos.value.data.isNotEmpty() -> {
                    Log.d("TAG", " VideoScreenUI is Success")
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(allVideos.value.data) {
                            Card(
                                onClick = {
                                    navController.navigate(PlayerScreen(videoUri = it.path.toString(), title = it.title))
                                }
                            ) {
                                Column {
                                    Text(text = it.path.toString())
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }

                }
            }
        }
      else {
          Text(text = "Plz Help")
      }
      }
  }
 */

    if (allVideos.value.isLoading) {
        CircularProgressIndicator()
    } else if (!allVideos.value.error.isNullOrEmpty()) {
        Text(text = allVideos.value.error!!)
    } else if (allVideos.value.data.isEmpty()) {
        Text(text = "No Videos Found")
    } else {
        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ){
            items(allVideos.value.data) {
                Card(
                    onClick = {
                        navController.navigate(PlayerScreen(videoUri = it.path.toString(), title = it.title))
                    }
                ) {
                    Column {
                        Text(text = it.path.toString())
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}


