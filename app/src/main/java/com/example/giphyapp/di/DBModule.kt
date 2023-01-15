package com.example.giphyapp.di

import android.content.Context
import androidx.room.Room
import com.example.giphyapp.data.local.DataBase.GiphyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun ProvideDB(
        @ApplicationContext context: Context
    ): GiphyDB{
        return Room.databaseBuilder(context, GiphyDB::class.java, "giphy_DB").build()
    }
}