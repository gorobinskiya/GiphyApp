package com.example.giphyapp.data.remote

import com.example.giphyapp.data.remote.dto.SearchResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface APIGiphyInterface {
    @GET("search")
    suspend fun search(
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("offset") offset:Int
    ) :SearchResultDto
}