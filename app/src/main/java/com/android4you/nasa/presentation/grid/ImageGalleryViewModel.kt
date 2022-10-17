package com.android4you.nasa.presentation.grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android4you.nasa.data.remote.NetworkState
import com.android4you.nasa.domain.model.ImageModelItem
import com.android4you.nasa.domain.usecase.GetAllImageGalleryUseCase
import com.android4you.nasa.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageGalleryViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val getAllImageGalleryUseCase: GetAllImageGalleryUseCase,
) : ViewModel() {

    private val stateflowPrivate = MutableStateFlow<ImageViewState>(ImageViewState.Empty)
    val stateflow: StateFlow<ImageViewState> get() = stateflowPrivate

    fun fetchAllImage() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                getAllImageGalleryUseCase.execute()
                    .flowOn(Dispatchers.IO)
                    .catch {
                    }
                    .collect { result ->
                        when (result) {
                            is NetworkState.Error -> {
                                stateflowPrivate.value = ImageViewState.Error("Service not available")
                            }
                            is NetworkState.Success -> {
                                stateflowPrivate.value = ImageViewState.Success(result.data)
                            }
                            else -> {}
                        }
                    }
            }
        } else {
            stateflowPrivate.value = ImageViewState.Error("No Internet Connection")
        }
    }
}
sealed class ImageViewState {
    data class Error(val message: String) : ImageViewState()
    object Empty : ImageViewState()
    data class Loading(val isLoading: Boolean) : ImageViewState()
    data class Success(val list: List<ImageModelItem>?) :
        ImageViewState()
}
