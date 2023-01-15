package com.example.giphyapp.data.local
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GiphyKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addKeys (keys: List<KeysEntity>)

    @Query("SELECT * FROM DB_KEYS WHERE id = :id")
    suspend fun getKeys(id: String):KeysEntity

    @Query ("DELETE FROM DB_KEYS")
    suspend fun deleteKeys()
}