package com.example.myvideoplayer.data_layer

data class VideoFile (

    val id : String?,
    val path : String?,
    val title : String?,
    val fileName : String,
    val size : String?,
    val duration : String?,
    val date : String?,
    val thumbnail : String? = null

)