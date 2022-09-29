package com.android4you.nasa.domain.repository

import com.android4you.nasa.data.remote.NetworkState
import com.android4you.nasa.domain.model.ImageModelItem
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun getAllImages(): Flow<NetworkState<List<ImageModelItem>>>
}
