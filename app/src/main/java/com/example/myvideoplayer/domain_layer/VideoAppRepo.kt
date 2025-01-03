package com.example.myvideoplayer.domain_layer

import android.app.Application
import com.example.myvideoplayer.State2
import com.example.myvideoplayer.data_layer.VideoFile
import kotlinx.coroutines.flow.Flow

interface VideoAppRepo {

    suspend fun getAllVideos(application: Application): Flow<State2<List<VideoFile>>>

    suspend fun getVideoByFolder(application: Application): Flow<State2<Map<String, List<VideoFile>>>>

}