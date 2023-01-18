package com.example.giphyapp.presentation.giflist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.usecase.BlockGifsUK
import com.example.giphyapp.usecase.SearchGifsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftListViewModel @Inject constructor(
    private val newsUseCase: SearchGifsUC,
    private val blockGifsUK: BlockGifsUK
) : ViewModel() {
    private val _gifs: MutableStateFlow<PagingData<GiphyEntity>?> = MutableStateFlow(null)
    val gifs: Flow<PagingData<GiphyEntity>> = _gifs.filterNotNull()

    fun launchSearch(searchQuery: String) {
        newsUseCase.searchGifs(searchQuery).cachedIn(viewModelScope).onEach { results ->
            _gifs.emit(results)
        }.launchIn(viewModelScope)
    }
    fun blockGifById(id: String) =viewModelScope.launch {
        blockGifsUK.blockGif(id)
    }
}