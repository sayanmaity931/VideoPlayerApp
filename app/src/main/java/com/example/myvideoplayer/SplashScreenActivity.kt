package com.example.myvideoplayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.myvideoplayer.ui_layer.view_model.MyViewModel
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        // Check and request permissions if needed
        checkPermissionsAndFetchVideos()
    }

    // Register the permission request activity result launcher
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.d("TAG", "Permission granted")
            viewModel.loadAllVideos()  // Fetch videos after permission is granted
        } else {
            Log.d("TAG", "Permission denied")
            // Handle permission denial (you can show a message or disable features)
        }
    }

    fun checkPermissionsAndFetchVideos() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_VIDEO)  // For Android 13 and above
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)  // For Android 10 and below
        }

        // Check if the permissions are granted
        val hasPermission = ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            // Request permission if not granted
            requestPermission.launch(permissions.toString())
        } else {
            // Proceed with fetching videos if permission is granted
            Log.d("TAG", "Permission granted, fetching videos...")
            viewModel.loadAllVideos()  // This is where you fetch the videos
        }
    }
}

