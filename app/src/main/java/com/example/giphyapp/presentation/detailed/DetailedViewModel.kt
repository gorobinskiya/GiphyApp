package com.example.giphyapp.presentation.detailed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.usecase.BlockGifsUK
import com.example.giphyapp.usecase.SearchGifsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel  @Inject constructor(
    private val newsUseCase: SearchGifsUC,
    private val blockGifsUK: BlockGifsUK
) : ViewModel() {
    private val _gifs: MutableStateFlow<PagingData<GiphyEntity>?> = MutableStateFlow(null)
    val gifs: Flow<PagingData<GiphyEntity>> = _gifs.filterNotNull()

    private val _chan = MutableLiveData(false)
    val chan: LiveData<Boolean> = _chan

    fun launchSearch(searchQuery: String) {
        newsUseCase.searchGifs(searchQuery).cachedIn(viewModelScope).onEach { results ->
            _gifs.emit(results).also {
                delay(100)
                if (_chan.value == false) {
                    _chan.value = true
                }
            }
        }.launchIn(viewModelScope)
    }
    fun blockGifById(id: String) =viewModelScope.launch {
        blockGifsUK.blockGif(id)
    }
}