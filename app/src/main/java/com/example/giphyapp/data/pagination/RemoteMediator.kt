package com.example.giphyapp.data.pagination

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.giphyapp.data.local.DataBase.GiphyDB
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.data.local.KeysEntity
import com.example.giphyapp.data.remote.APIGiphyInterface
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RemoteMediator(private val db: GiphyDB,
                     private val apiService: APIGiphyInterface,
                     private val searchQuery: String
) : RemoteMediator<Int, GiphyEntity>() {

    private val giphyDao = db.giphyDao()
    private val giphyRemoteKeysDao = db.giphyKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GiphyEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiService.search(
                searchQuery = searchQuery,
                offset = currentPage,
                limit = 10
            )
            for (i in response.data) {
                Log.d("myLog", "Item id: ${i.id} title: ${i.title}")
            }

            val endOfPaginationReached = response.data.isEmpty() ?: true

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {

                }
                val keys = response.data.map { unsplashImage ->
                    KeysEntity(
                        id = unsplashImage.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys.let { giphyRemoteKeysDao.addKeys(keys = it) }
                response.data.let {
                    giphyDao.addGifs(
                        gifs = it.map { giphyDto ->
                            giphyDto.toGiphyEntity(searchQuery)
                        })
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GiphyEntity>
    ): KeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                giphyRemoteKeysDao.getKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, GiphyEntity>
    ): KeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                giphyRemoteKeysDao.getKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GiphyEntity>
    ): KeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                giphyRemoteKeysDao.getKeys(id = unsplashImage.id)
            }
    }

}