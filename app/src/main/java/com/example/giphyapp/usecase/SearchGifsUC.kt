package com.example.giphyapp.usecase

import androidx.paging.PagingData
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.domain.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGifsUC @Inject constructor(
    private val repository: GiphyRepository
) {
    fun searchGifs(searchQuery: String): Flow<PagingData<GiphyEntity>> {
        return repository.searchGifs(searchQuery)
    }
}