package com.example.giphyapp.data.local
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GiphyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGifs (gifs: List<GiphyEntity>)

    @Query("SELECT * FROM DB_GIFS WHERE blocked = 0 AND searchQuery LIKE '%' || :searchQuery || '%'")
    fun getAllNotBlockedGifsByQuery(searchQuery: String): PagingSource<Int, GiphyEntity>

    @Query("Update DB_GIFS SET blocked = 1 WHERE id = :id")
    suspend fun markBlockedGifs(id: String)

    @Query ("DELETE FROM DB_GIFS")
    suspend fun deleteGifs()
}