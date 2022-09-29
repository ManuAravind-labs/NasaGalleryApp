package com.android4you.nasa.data.remote

sealed class NetworkState<T>(val data: T? = null, val message: String? = null) {
    class Empty<T> : NetworkState<T>()
    class Loading<T> : NetworkState<T>()
    class Success<T>(data: T) : NetworkState<T>(data, null)
    class Error<T>(message: String, data: T? = null) : NetworkState<T>(null, message)
}
