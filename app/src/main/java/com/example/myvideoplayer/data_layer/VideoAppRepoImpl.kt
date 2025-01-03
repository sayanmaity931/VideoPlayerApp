package com.example.myvideoplayer.data_layer

import android.Manifest
import android.app.Application
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.database.getStringOrNull
import com.example.myvideoplayer.SplashScreenActivity
import com.example.myvideoplayer.State2
import com.example.myvideoplayer.domain_layer.VideoAppRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import java.io.File


class VideoAppRepoImpl : VideoAppRepo {
    override suspend fun getAllVideos(application: Application): Flow<State2<List<VideoFile>>> =
        flow {

            emit(State2.Loading)
            Log.d("TAG", "Fetching videos...")

            val allVideos = ArrayList<VideoFile>()

            try {
                // projection means from storage what you want to fetch
                val projection = arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.DATE_ADDED,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.DISPLAY_NAME
                )

                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                Log.d("TAG", "Querying URI: $uri")
                // cursor is used to read data from storage that's why we have used content resolver
                val cursor = application.contentResolver.query(uri, projection, null, null, null)

                val hasPermission = ContextCompat.checkSelfPermission(application, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                Log.d("TAG", "Permission Granted: $hasPermission")

                if (cursor != null) {

                    // moveToNext is used to move to next row and while moving it also checks that whether the next object is null or not
                    while (cursor.moveToNext()) {
                        
                        val id = cursor.getStringOrNull(0)
                        val path = cursor.getStringOrNull(1)
                        val title = cursor.getStringOrNull(2)
                        val size = cursor.getStringOrNull(3)
                        val dateAdded = cursor.getStringOrNull(4)
                        val duration = cursor.getStringOrNull(5)
                        val fileName = cursor.getStringOrNull(6)

                        val thumbnail = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            id?.toLong() ?: 0
                        )

                        val videoFile = VideoFile(
                            id = id,
                            path = path,
                            title = title,
                            fileName = fileName.toString(),
                            size = size,
                            date = dateAdded,
                            duration = duration,
                            thumbnail = thumbnail.toString()
                        )

                        if (cursor.count == 0) {
                            Log.d("TAG", "Query successful but no videos found.")
                        }

                        val file = File(path)

                        if (file.exists()) {
                            allVideos.add(videoFile)
                        } else {
                            Log.d("TAG", "File does not exist: $path")
                        }

                        allVideos.add(videoFile)

                        if (cursor.isLast) {
                            break
                        }
                    }
                } else {
                    Log.d("TAG", "Cursor is null")
                }
                cursor?.close()
                emit(State2.Success(allVideos))
            }
            catch (e: Exception) {
                Log.e("TAG", "Error fetching videos: ${e.message}")
                emit(State2.Error(e.message ?: "Unknown error"))
            }
        }

    override suspend fun getVideoByFolder(application: Application): Flow<State2<Map<String, List<VideoFile>>>> =
        channelFlow {

           try {
               getAllVideos(application).collectLatest { result ->

                   when (result) {
                       is State2.Success -> {
                           val videoByFolder = result.data.groupBy {
                               File(it.path).parentFile?.name ?: "Unknown"
                           }
                           send(State2.Success(videoByFolder))
                       }

                       is State2.Error -> {
                           send(State2.Error(result.message))
                       }

                       is State2.Loading -> {
                           send(State2.Loading)
                       }
                   }
               }
           }catch (e : IllegalStateException){
               e.printStackTrace()
           }
        }
    }

