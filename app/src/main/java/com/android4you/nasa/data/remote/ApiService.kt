package com.android4you.nasa.data.remote

import com.android4you.nasa.domain.model.ImageModelItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v1/dfb10e10-fc43-4887-946d-2fea3c3e77a4")
    suspend fun getAllImages(): Response<List<ImageModelItem>>
}
