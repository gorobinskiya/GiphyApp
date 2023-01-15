package com.example.giphyapp.data.remote.dto

data class SearchResultDto(
    val `data`: MutableList<Data>,
    val meta: Meta,
    val pagination: Pagination
)