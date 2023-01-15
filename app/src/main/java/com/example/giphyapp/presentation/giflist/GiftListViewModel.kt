package com.example.giphyapp.presentation.giflist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GiftListViewModel @Inject constructor() : ViewModel() {
    fun getSearch() {
        viewModelScope.launch {

        }
    }
}