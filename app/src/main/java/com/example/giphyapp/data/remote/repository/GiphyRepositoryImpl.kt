package com.example.giphyapp.data.remote.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.giphyapp.data.local.DataBase.GiphyDB
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.data.pagination.RemoteMediator
import com.example.giphyapp.data.remote.APIGiphyInterface
import com.example.giphyapp.domain.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)

class GiphyRepositoryImpl @Inject constructor(
    private val db: GiphyDB,
    private val apiService: APIGiphyInterface) : GiphyRepository {
    override fun searchGifs(searchQuery: String): Flow<PagingData<GiphyEntity>> {
        val pagingSourceFactory = { db.giphyDao().getAllNotBlockedGifsByQuery(searchQuery) }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = RemoteMediator(
                db, apiService, searchQuery
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun blockGif(id: String) {
        db.giphyDao().markBlockedGifs(id)
    }
}