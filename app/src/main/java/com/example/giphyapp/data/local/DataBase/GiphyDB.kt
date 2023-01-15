package com.example.giphyapp.data.local.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.giphyapp.data.local.GiphyDao
import com.example.giphyapp.data.local.GiphyEntity
import com.example.giphyapp.data.local.GiphyKeysDao
import com.example.giphyapp.data.local.KeysEntity

@Database(entities = [GiphyEntity::class, KeysEntity::class], version = 1)
abstract class GiphyDB: RoomDatabase() {
    abstract fun giphyDao(): GiphyDao
    abstract fun giphyKeysDao(): GiphyKeysDao
}