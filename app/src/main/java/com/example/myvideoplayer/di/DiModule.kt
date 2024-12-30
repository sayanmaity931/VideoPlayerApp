package com.example.myvideoplayer.di

import com.example.myvideoplayer.data_layer.VideoAppRepoImpl
import com.example.myvideoplayer.domain_layer.VideoAppRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Singleton
    @Provides
    fun provideVideoAppRepo() : VideoAppRepo = VideoAppRepoImpl()

}