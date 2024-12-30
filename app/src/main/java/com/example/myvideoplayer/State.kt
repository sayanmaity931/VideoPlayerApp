package com.example.myvideoplayer

sealed class State2<out T> {
    data class Success<out T>(val data: T) : State2<T>()
    data class Error(val message: String) : State2<Nothing>()
    object Loading : State2<Nothing>()
}