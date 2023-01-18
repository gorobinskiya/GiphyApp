package com.example.giphyapp.di

import com.example.giphyapp.data.local.DataBase.GiphyDB
import com.example.giphyapp.data.remote.APIGiphyInterface
import com.example.giphyapp.data.remote.repository.GiphyRepositoryImpl
import com.example.giphyapp.domain.GiphyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideGiphyRepository(
        db: GiphyDB,
        apiService: APIGiphyInterface
    ) = GiphyRepositoryImpl(
        db,
        apiService
    ) as GiphyRepository
}