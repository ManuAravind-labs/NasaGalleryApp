package com.android4you.nasa.domain.usecase

import com.android4you.nasa.data.remote.NetworkState
import com.android4you.nasa.domain.model.ImageModelItem
import com.android4you.nasa.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllImageGalleryUseCase @Inject constructor(
    private val appRepository: ImageRepository
) {
    suspend fun execute(): Flow<NetworkState<List<ImageModelItem>>> {
        return appRepository.getAllImages()
    }
}
