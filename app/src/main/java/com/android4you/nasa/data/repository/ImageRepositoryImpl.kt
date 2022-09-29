package com.android4you.nasa.data.repository

import com.android4you.nasa.data.remote.ApiService
import com.android4you.nasa.data.remote.NetworkState
import com.android4you.nasa.domain.model.ImageModelItem
import com.android4you.nasa.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ImageRepository {

    override suspend fun getAllImages(): Flow<NetworkState<List<ImageModelItem>>> {
        return flow {
            val response = apiService.getAllImages()
            if (response.isSuccessful) {
                // val list = response.body()?.asReversed()
                val list = response.body()?.sortedByDescending {
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    sdf.parse(it.date)?.time
                }
                emit(NetworkState.Success(list!!))
            } else {
                emit(NetworkState.Error(response.message()))
            }
        }
    }
}
