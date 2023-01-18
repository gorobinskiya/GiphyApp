package com.example.giphyapp.usecase

import com.example.giphyapp.domain.GiphyRepository
import javax.inject.Inject

class BlockGifsUK @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend fun blockGif(id: String) {
        return repository.blockGif(id)
    }
}