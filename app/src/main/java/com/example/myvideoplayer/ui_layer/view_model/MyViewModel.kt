package com.example.myvideoplayer.ui_layer.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvideoplayer.State2
import com.example.myvideoplayer.data_layer.VideoFile
import com.example.myvideoplayer.domain_layer.VideoAppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val application: Application ,private val repo : VideoAppRepo) : ViewModel() {

    private val _getAllVideos = MutableStateFlow(GetAllVideosState())
    val getAllVideos = _getAllVideos.asStateFlow()

    fun loadAllVideos() {
        Log.d("TAG", "loadAllVideos called")
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllVideos(application = application).collect {
                when (it) {
                    is State2.Success -> {
                        Log.d("TAG", "Fetched Videos: ${it.data}")
                        _getAllVideos.value = GetAllVideosState(data = it.data , isLoading = false)
                        Log.d("TAG","State is Success")
                    }

                    is State2.Error -> {
                        _getAllVideos.value = GetAllVideosState(error = it.message , isLoading = false)
                        Log.d("TAG", "Error occurred: ${it.message}")
                    }

                    is State2.Loading -> {
                        _getAllVideos.value = GetAllVideosState(isLoading = true)
                        Log.d("TAG","State is Loading")
                    }
                }
            }
        }
    }

    init {
        loadAllVideos()
        Log.d("TAG", "ViewModel Initialized")
    }
}
data class GetAllVideosState(
    val isLoading : Boolean = false,
    val data : List<VideoFile> = emptyList(),
    val error : String? = ""
)