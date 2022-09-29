package com.android4you.nasa.data.di

import com.android4you.nasa.data.remote.ApiService
import com.android4you.nasa.data.repository.ImageRepositoryImpl
import com.android4you.nasa.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLaunchesRepository(
        apiService: ApiService
    ): ImageRepository {
        return ImageRepositoryImpl(apiService)
    }

}
