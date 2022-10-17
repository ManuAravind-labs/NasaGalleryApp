package com.android4you.nasa.presentation.grid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android4you.nasa.MainCoroutineRule
import com.android4you.nasa.data.TestDataGenerator
import com.android4you.nasa.data.remote.NetworkState
import com.android4you.nasa.domain.model.ImageModelItem
import com.android4you.nasa.domain.usecase.GetAllImageGalleryUseCase
import com.android4you.nasa.utils.NetworkHelper
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.InputStreamReader

class ImageGalleryViewModelTest {

    private val testModelsGenerator: TestDataGenerator = TestDataGenerator()

    // Subject under test
    private lateinit var underTest: ImageGalleryViewModel

    // Use a fake UseCase to be injected into the viewModel
    @MockK
    private lateinit var getAllImageGalleryUseCase: GetAllImageGalleryUseCase

    @MockK
    private lateinit var networkHelper: NetworkHelper

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getImageListResponse()
    }

    private var imageGalleryList: List<ImageModelItem> = ArrayList()

    fun getImageListResponse(): List<ImageModelItem> {
        val data = getJson("Success.json")
        val gson = Gson()
        val type = object : TypeToken<List<ImageModelItem>>() {}.type
        imageGalleryList = gson.fromJson(data, type)
        return imageGalleryList
    }

    private fun getJson(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }

    @Test
    fun test_imageGallery_api_response_empty_case() {
        // given
        coEvery {
            getAllImageGalleryUseCase.execute()
        } returns flow {
            emit(NetworkState.Success(emptyList()))
        }

        coEvery {
            networkHelper.isNetworkConnected()
        } returns true
        underTest = ImageGalleryViewModel(networkHelper, getAllImageGalleryUseCase)
        // when
        underTest.fetchAllImage()
        // then
        runBlocking {
            assertEquals(ImageViewState.Success(emptyList()), underTest.stateflow.value)
        }
    }

    @Test
    fun test_imageGallery_api_response_in_success_case() {
        // given
        coEvery {
            networkHelper.isNetworkConnected()
        } returns true
        coEvery {
            getAllImageGalleryUseCase.execute()
        } returns flow {
            emit(NetworkState.Success(testModelsGenerator.getImageListResponse()))
        }

        underTest = ImageGalleryViewModel(networkHelper, getAllImageGalleryUseCase)
        // when
        underTest.fetchAllImage()
        // then
        runBlocking {
            val actualValue = underTest.stateflow.value
            assertEquals(ImageViewState.Success(imageGalleryList), actualValue)
        }
    }

    @Test
    fun test_no_internet_first_time() {
        // given
        coEvery {
            networkHelper.isNetworkConnected()
        } returns false
        coEvery {
            getAllImageGalleryUseCase.execute()
        } returns flow {
            ImageViewState.Error("No Internet Connection")
        }

        underTest = ImageGalleryViewModel(networkHelper, getAllImageGalleryUseCase)
        // when
        underTest.fetchAllImage()
        // then
        runBlocking {
            assertEquals(ImageViewState.Error("No Internet Connection"), underTest.stateflow.value)
        }
    }
}
