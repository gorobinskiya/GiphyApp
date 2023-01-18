package com.example.giphyapp.domain

import androidx.paging.PagingData
import com.example.giphyapp.data.local.GiphyEntity
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
    fun searchGifs(searchQuery: String): Flow<PagingData<GiphyEntity>>
    suspend fun blockGif(id: String)
}