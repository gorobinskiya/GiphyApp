package com.example.giphyapp.data.local


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "db_gifs")
@Parcelize
data class GiphyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val url: String,
    val searchQuery: String,
    val blocked: Boolean
) : Parcelable