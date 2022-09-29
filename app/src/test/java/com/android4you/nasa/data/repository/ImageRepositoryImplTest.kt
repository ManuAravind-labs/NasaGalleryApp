package com.android4you.nasa.data.repository

import com.android4you.nasa.data.TestDataGenerator
import com.android4you.nasa.data.remote.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ImageRepositoryImplTest {

    private lateinit var mainRepository: ImageRepositoryImpl

    @MockK
    lateinit var apiService: ApiService

    private val testModelsGenerator: TestDataGenerator = TestDataGenerator()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainRepository = ImageRepositoryImpl(apiService)
    }

    @Test
    fun test_get_all_images_from_service_successfully() {
        val expectedResponse = testModelsGenerator.getImageListResponse()

        coEvery {
            apiService.getAllImages()
        } returns Response.success(testModelsGenerator.getImageListResponse())

        runBlocking {
            val response = mainRepository.getAllImages()
            assertEquals(expectedResponse, response.first().data)
            assertEquals(expectedResponse[0].date, response.first().data?.get(0)?.date)
            assertEquals(
                expectedResponse[0].explanation,
                response.first().data?.get(0)?.explanation
            )
            assertEquals(expectedResponse[0].copyright, response.first().data?.get(0)?.copyright)
        }
    }
}
